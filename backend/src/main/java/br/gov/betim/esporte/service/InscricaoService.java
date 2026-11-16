package br.gov.betim.esporte.service;

import br.gov.betim.esporte.dto.InscricaoRequest;
import br.gov.betim.esporte.model.Inscricao;
import br.gov.betim.esporte.repository.InscricaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final ObjectMapper json;
    private static final String VERSAO_TERMO_IMAGEM = "2026-11-04";

    public InscricaoService(InscricaoRepository inscricaoRepository, ObjectMapper json) {
        this.inscricaoRepository = inscricaoRepository;
        this.json = json;
    }

    public List<Inscricao> listar() {
        return inscricaoRepository.findAll();
    }

    public Inscricao criar(InscricaoRequest request) {
        validarFicha(request);
        if ((request.autorizaUsoImagem() != null || request.termoImagemVersao() != null)
                && !VERSAO_TERMO_IMAGEM.equals(request.termoImagemVersao())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reabra o termo de imagem antes de concluir.");
        }
        Inscricao inscricao = new Inscricao();
        inscricao.setNomeCompleto(request.nomeCompleto());
        inscricao.setCpf(request.cpf());
        inscricao.setEmail(request.email());
        inscricao.setTelefone(request.telefone());
        inscricao.setModalidade(request.modalidade());
        inscricao.setDataInscricao(LocalDate.now());
        inscricao.setAutorizaUsoImagem(request.autorizaUsoImagem());
        inscricao.setTermoImagemVersao(request.termoImagemVersao());
        inscricao.setDecisaoImagemEm(request.autorizaUsoImagem() == null ? null : Instant.now());
        inscricao.setDadosFicha(request.dadosFicha() == null ? null : json.valueToTree(request.dadosFicha()).toString());
        return inscricaoRepository.save(inscricao);
    }

    private void validarFicha(InscricaoRequest request) {
        if (request == null || vazio(request.nomeCompleto()) || vazio(request.telefone()) || vazio(request.modalidade())) {
            throw erro("Preencha nome, telefone e modalidade.");
        }
        Map<String, String> ficha = request.dadosFicha();
        if (ficha == null) throw erro("Envie a ficha completa do participante.");
        String nomeFicha = exigir(ficha, "nome", "nome do participante");
        String nascimento = exigir(ficha, "dataNascimento", "data de nascimento");
        String modalidadeFicha = exigir(ficha, "modalidade", "modalidade");
        if (!request.nomeCompleto().trim().equalsIgnoreCase(nomeFicha)
                || !request.modalidade().trim().equalsIgnoreCase(modalidadeFicha)) {
            throw erro("Os dados principais da ficha não conferem com o cadastro.");
        }
        exigir(ficha, "nucleo", "núcleo");
        exigir(ficha, "dataDeclaracao", "data da declaração");
        exigir(ficha, "assinatura", "assinatura");
        if (!aceito(ficha.get("aceiteDeclaracao"))) throw erro("Aceite a declaração da ficha.");

        LocalDate dataNascimento = data(nascimento, "data de nascimento");
        if (dataNascimento.isAfter(LocalDate.now())) throw erro("A data de nascimento não pode estar no futuro.");
        if (Period.between(dataNascimento, LocalDate.now()).getYears() > 120) throw erro("Confira a data de nascimento.");
        LocalDate dataDeclaracao = data(ficha.get("dataDeclaracao"), "data da declaração");
        if (dataDeclaracao.isAfter(LocalDate.now())) throw erro("A data da declaração não pode estar no futuro.");

        String cpf = vazio(request.cpf()) ? ficha.get("cpf") : request.cpf();
        if (!vazio(cpf) && !cpfValido(cpf)) throw erro("Informe um CPF válido.");
        if (!telefoneValido(request.telefone())) throw erro("Informe um telefone válido.");
        if (Period.between(dataNascimento, LocalDate.now()).getYears() < 18) {
            exigir(ficha, "nomeResponsavel", "nome do responsável");
            exigir(ficha, "cpfResponsavel", "CPF do responsável");
            exigir(ficha, "telefoneResponsavel", "telefone do responsável");
            if (!cpfValido(ficha.get("cpfResponsavel"))) throw erro("Informe um CPF válido para o responsável.");
            if (!telefoneValido(ficha.get("telefoneResponsavel"))) throw erro("Informe um telefone válido para o responsável.");
        }
    }

    private static String exigir(Map<String, String> ficha, String campo, String nome) {
        String valor = ficha.get(campo);
        if (vazio(valor)) throw erro("Preencha " + nome + ".");
        return valor.trim();
    }

    private static LocalDate data(String valor, String nome) {
        try { return LocalDate.parse(valor); }
        catch (DateTimeParseException | NullPointerException ex) { throw erro("Informe " + nome + " no formato válido."); }
    }

    private static boolean telefoneValido(String valor) { return valor != null && valor.replaceAll("\\D", "").matches("\\d{10,11}"); }

    private static boolean cpfValido(String valor) {
        String cpf = valor == null ? "" : valor.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) return false;
        for (int tamanho = 9; tamanho <= 10; tamanho++) {
            int soma = 0;
            for (int i = 0; i < tamanho; i++) soma += (cpf.charAt(i) - '0') * (tamanho + 1 - i);
            int digito = (soma * 10) % 11;
            if (digito == 10) digito = 0;
            if (digito != cpf.charAt(tamanho) - '0') return false;
        }
        return true;
    }

    private static boolean aceito(String valor) { return "true".equalsIgnoreCase(valor) || "on".equalsIgnoreCase(valor) || "sim".equalsIgnoreCase(valor); }
    private static boolean vazio(String valor) { return valor == null || valor.isBlank(); }
    private static ResponseStatusException erro(String mensagem) { return new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagem); }
}
