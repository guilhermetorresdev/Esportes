package br.gov.betim.esporte;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = { "spring.datasource.url=jdbc:h2:mem:almox_api_test;DB_CLOSE_DELAY=-1", "spring.jpa.hibernate.ddl-auto=create-drop", "logging.level.root=WARN", "debug=false" })
@AutoConfigureMockMvc
class AlmoxarifadoApiTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;

    @Test
    void operadorAdminRegistraConsultaEDevolveComControleDeVersao() throws Exception {
        MockHttpSession sessao = login();
        String retirada = "{\"material\":\"Bola de futsal\",\"categoriaMaterial\":\"Futsal\",\"quantidade\":4,\"evento\":\"Festival\",\"estadoRetirada\":\"Bom\",\"responsavelRetirada\":\"Instrutor\",\"responsavelEntrega\":\"Almoxarifado\",\"dataEntrega\":\"2026-11-16\",\"previsaoDevolucao\":\"2026-11-20\",\"observacoesRetirada\":\"Teste\"}";
        String corpo = mvc.perform(post("/api/almoxarifado/retiradas").with(csrf()).session(sessao).contentType(MediaType.APPLICATION_JSON).content(retirada))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("EM_USO")).andReturn().getResponse().getContentAsString();
        JsonNode item = json.readTree(corpo); long id = item.get("id").asLong(); long versao = item.get("versao").asLong();
        mvc.perform(get("/api/almoxarifado").session(sessao)).andExpect(status().isOk()).andExpect(jsonPath("$[0].material").value("Bola de futsal"));
        String primeira = "{\"versao\":%d,\"quantidadeDevolvida\":2,\"dataDevolucao\":\"2026-11-18\",\"estadoDevolucao\":\"Bom\",\"responsavelRecebimento\":\"Almoxarifado\",\"observacoesDevolucao\":\"Parcial\"}".formatted(versao);
        String acimaDoSaldo = primeira.replace("\"quantidadeDevolvida\":2", "\"quantidadeDevolvida\":5");
        mvc.perform(post("/api/almoxarifado/{id}/devolucao", id).with(csrf()).session(sessao).contentType(MediaType.APPLICATION_JSON).content(acimaDoSaldo)).andExpect(status().isBadRequest());
        String corpoParcial = mvc.perform(post("/api/almoxarifado/{id}/devolucao", id).with(csrf()).session(sessao).contentType(MediaType.APPLICATION_JSON).content(primeira))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("DEVOLUCAO_PARCIAL")).andReturn().getResponse().getContentAsString();
        long versaoParcial = json.readTree(corpoParcial).get("versao").asLong();
        String segunda = "{\"versao\":%d,\"quantidadeDevolvida\":2,\"dataDevolucao\":\"2026-11-19\",\"estadoDevolucao\":\"Bom\",\"responsavelRecebimento\":\"Almoxarifado\",\"observacoesDevolucao\":\"Final\"}".formatted(versaoParcial);
        mvc.perform(post("/api/almoxarifado/{id}/devolucao", id).with(csrf()).session(sessao).contentType(MediaType.APPLICATION_JSON).content(segunda))
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("DEVOLVIDO")).andExpect(jsonPath("$.quantidadeDevolvida").value(4));
        mvc.perform(post("/api/almoxarifado/{id}/devolucao", id).with(csrf()).session(sessao).contentType(MediaType.APPLICATION_JSON).content(segunda))
                .andExpect(status().isConflict());
    }

    @Test
    void visitanteNaoAlteraMovimentacao() throws Exception {
        mvc.perform(post("/api/almoxarifado/retiradas").with(csrf()).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isUnauthorized());
    }

    private MockHttpSession login() throws Exception {
        return (MockHttpSession) mvc.perform(post("/login").param("username", "admin").param("password", "admin123"))
                .andExpect(status().isOk()).andReturn().getRequest().getSession(false);
    }
}
