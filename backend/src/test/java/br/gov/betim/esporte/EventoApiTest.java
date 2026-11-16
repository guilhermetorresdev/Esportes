package br.gov.betim.esporte;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:evento_api_test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop", "logging.level.root=WARN", "debug=false"
})
@AutoConfigureMockMvc
class EventoApiTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;

    private static final String EVENTO = """
            {"nome":"Festival de Futsal","categoria":"Sub-15","descricao":"Final municipal",
             "dataEvento":"2026-12-10","hora":"14:00","local":"Horto","responsavel":"Semes","materiais":"Bolas"}
            """;

    @Test
    void visitanteConsultaEAdministradorGerenciaMesmoRegistro() throws Exception {
        mvc.perform(get("/api/eventos")).andExpect(status().isOk());
        mvc.perform(post("/api/eventos").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(EVENTO))
                .andExpect(status().isUnauthorized());
        MockHttpSession sessao = (MockHttpSession) mvc.perform(post("/login")
                        .param("username", "admin").param("password", "admin123"))
                .andExpect(status().isOk()).andReturn().getRequest().getSession(false);
        String criado = mvc.perform(post("/api/eventos").with(csrf()).session(sessao)
                        .contentType(MediaType.APPLICATION_JSON).content(EVENTO))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();
        long id = json.readTree(criado).get("id").asLong();
        mvc.perform(get("/api/eventos/{id}", id)).andExpect(status().isOk()).andExpect(jsonPath("$.categoria").value("Sub-15"));
        mvc.perform(put("/api/eventos/{id}", id).with(csrf()).session(sessao)
                        .contentType(MediaType.APPLICATION_JSON).content(EVENTO.replace("Festival de Futsal", "Festival atualizado")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Festival atualizado"));
        mvc.perform(delete("/api/eventos/{id}", id).with(csrf()).session(sessao)).andExpect(status().isNoContent());
        mvc.perform(get("/api/eventos/{id}", id)).andExpect(status().isNotFound());
    }
}
