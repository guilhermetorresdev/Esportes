package br.gov.betim.esporte;

import br.gov.betim.esporte.repository.InscricaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:inscricao_imagem_test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop", "spring.jpa.show-sql=false",
        "debug=false", "logging.level.root=WARN"
})
@AutoConfigureMockMvc
class InscricaoImagemTest {
    @Autowired MockMvc mvc;
    @Autowired InscricaoRepository inscricoes;
    @Autowired ObjectMapper json;

    @BeforeEach
    void limpar() { inscricoes.deleteAll(); }

    @Test
    void registraAutorizacaoExpressaComVersaoEData() throws Exception {
        mvc.perform(post("/api/inscricoes").contentType(MediaType.APPLICATION_JSON).content("""
                {"nomeCompleto":"Participante teste","telefone":"31999999999","modalidade":"futebol","dadosFicha":{"nome":"Participante teste","modalidade":"futebol","dataNascimento":"2000-04-12","nucleo":"Horto","dataDeclaracao":"2026-11-16","assinatura":"Participante teste","aceiteDeclaracao":"true"},
                 "autorizaUsoImagem":true,"termoImagemVersao":"2026-11-04"}
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.autorizaUsoImagem").value(true))
                .andExpect(jsonPath("$.termoImagemVersao").value("2026-11-04"))
                .andExpect(jsonPath("$.decisaoImagemEm").isNotEmpty());
    }

    @Test
    void recusaNaoImpedeInscricaoEPreservaTodosOsDadosDaFicha() throws Exception {
        mvc.perform(post("/api/inscricoes").contentType(MediaType.APPLICATION_JSON).content("""
                {"nomeCompleto":"Participante teste","telefone":"31999999999","modalidade":"volei",
                 "autorizaUsoImagem":false,"termoImagemVersao":"2026-11-04",
                 "dadosFicha":{"nome":"Participante teste","modalidade":"volei","nomeResponsavel":"Responsável teste","dataNascimento":"2015-04-12",
                               "nucleo":"Horto Municipal","alergias":"Informação de teste","cpfResponsavel":"529.982.247-25","telefoneResponsavel":"31988887777","dataDeclaracao":"2026-11-16","assinatura":"Responsável teste","aceiteDeclaracao":"true"}}
                """))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.autorizaUsoImagem").value(false))
                .andExpect(jsonPath("$.decisaoImagemEm").isNotEmpty());
        var inscricao = inscricoes.findAll().get(0);
        assertThat(inscricao.getAutorizaUsoImagem()).isFalse();
        var ficha = json.readTree(inscricao.getDadosFicha());
        assertThat(ficha.get("nomeResponsavel").asText()).isEqualTo("Responsável teste");
        assertThat(ficha.get("alergias").asText()).isEqualTo("Informação de teste");
    }

    @Test
    void permiteContinuarSemResponderAoTermo() throws Exception {
        mvc.perform(post("/api/inscricoes").contentType(MediaType.APPLICATION_JSON).content("""
                {"nomeCompleto":"Participante teste","telefone":"31999999999","modalidade":"pilates","dadosFicha":{"nome":"Participante teste","modalidade":"pilates","dataNascimento":"2000-04-12","nucleo":"Horto","dataDeclaracao":"2026-11-16","assinatura":"Participante teste","aceiteDeclaracao":"true"},
                 "autorizaUsoImagem":null,"termoImagemVersao":"2026-11-04"}
                """))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.autorizaUsoImagem").doesNotExist())
                .andExpect(jsonPath("$.decisaoImagemEm").doesNotExist());
        assertThat(inscricoes.findAll().get(0).getAutorizaUsoImagem()).isNull();
    }

    @Test
    void aceitaClientesAnterioresSemConsentimentoImplicito() throws Exception {
        mvc.perform(post("/api/inscricoes").contentType(MediaType.APPLICATION_JSON).content("""
                {"nomeCompleto":"Participante teste","telefone":"31999999999","modalidade":"futebol","dadosFicha":{"nome":"Participante teste","modalidade":"futebol","dataNascimento":"2000-04-12","nucleo":"Horto","dataDeclaracao":"2026-11-16","assinatura":"Participante teste","aceiteDeclaracao":"true"}}
                """))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.autorizaUsoImagem").doesNotExist())
                .andExpect(jsonPath("$.termoImagemVersao").doesNotExist());
    }

    @Test
    void rejeitaDecisaoSemVersaoDoTermoOuVersaoDiferente() throws Exception {
        for (String extra : new String[]{"\"autorizaUsoImagem\":true", "\"autorizaUsoImagem\":false",
                "\"autorizaUsoImagem\":true,\"termoImagemVersao\":\"antiga\""}) {
            mvc.perform(post("/api/inscricoes").contentType(MediaType.APPLICATION_JSON).content(
                    "{\"nomeCompleto\":\"Teste\",\"telefone\":\"31999999999\",\"modalidade\":\"futebol\"," + extra + "}"))
                    .andExpect(status().isBadRequest());
        }
        assertThat(inscricoes.count()).isZero();
    }

    @Test
    void naoCriaInscricaoVazia() throws Exception {
        mvc.perform(post("/api/inscricoes").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
        assertThat(inscricoes.count()).isZero();
    }

    @Test
    void rejeitaCpfInvalidoSemGravar() throws Exception {
        mvc.perform(post("/api/inscricoes").contentType(MediaType.APPLICATION_JSON).content("""
                {"nomeCompleto":"Teste","cpf":"111.111.111-11","telefone":"31999999999","modalidade":"futebol",
                 "dadosFicha":{"nome":"Teste","cpf":"111.111.111-11","dataNascimento":"2000-04-12","nucleo":"Horto","dataDeclaracao":"2026-11-16","assinatura":"Teste","aceiteDeclaracao":"true"}}
                """))
                .andExpect(status().isBadRequest());
        assertThat(inscricoes.count()).isZero();
    }

    @Test
    void exigeResponsavelParaMenor() throws Exception {
        mvc.perform(post("/api/inscricoes").contentType(MediaType.APPLICATION_JSON).content("""
                {"nomeCompleto":"Menor teste","telefone":"31999999999","modalidade":"futebol",
                 "dadosFicha":{"nome":"Menor teste","dataNascimento":"2015-04-12","nucleo":"Horto","dataDeclaracao":"2026-11-16","assinatura":"Menor teste","aceiteDeclaracao":"true"}}
                """))
                .andExpect(status().isBadRequest());
        assertThat(inscricoes.count()).isZero();
    }
}
