package br.gov.betim.esporte;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:csrf_api_test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "logging.level.root=WARN",
        "debug=false"
})
@AutoConfigureMockMvc
class CsrfApiTest {

    @Autowired
    MockMvc mvc;

    @Test
    void publicaTokenNoCookieEJson() throws Exception {
        mvc.perform(get("/api/auth/csrf"))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("XSRF-TOKEN"))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void rejeitaMutacaoSemToken() throws Exception {
        mvc.perform(post("/api/conquistas/atualizar"))
                .andExpect(status().isForbidden());
    }

    @Test
    void tokenValidoAvancaAteAutorizacao() throws Exception {
        MockHttpServletResponse resposta = mvc.perform(get("/api/auth/csrf"))
                .andReturn().getResponse();
        String token = resposta.getCookie("XSRF-TOKEN").getValue();

        mvc.perform(post("/api/conquistas/atualizar")
                        .cookie(resposta.getCookie("XSRF-TOKEN"))
                        .header("X-XSRF-TOKEN", token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rejeitaTokenInvalido() throws Exception {
        mvc.perform(post("/api/conquistas/atualizar")
                        .header("X-XSRF-TOKEN", "token-invalido"))
                .andExpect(status().isForbidden());
    }
}
