package br.gov.betim.esporte.service;

import br.gov.betim.esporte.dto.AlmoxarifadoDevolucaoRequest;
import br.gov.betim.esporte.dto.AlmoxarifadoMovimentacaoRequest;
import br.gov.betim.esporte.model.AlmoxarifadoMovimentacao;
import br.gov.betim.esporte.repository.AlmoxarifadoMovimentacaoRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AlmoxarifadoService {
    private final AlmoxarifadoMovimentacaoRepository repository;
    private final br.gov.betim.esporte.repository.AlmoxarifadoDevolucaoRepository devolucaoRepository;
    public AlmoxarifadoService(AlmoxarifadoMovimentacaoRepository repository, br.gov.betim.esporte.repository.AlmoxarifadoDevolucaoRepository devolucaoRepository) { this.repository = repository; this.devolucaoRepository = devolucaoRepository; }

    public List<AlmoxarifadoMovimentacao> listar() { return repository.findAll(); }
    public AlmoxarifadoMovimentacao buscar(Long id) { return repository.findById(id).orElseThrow(() -> erro(HttpStatus.NOT_FOUND, "Movimentação não encontrada.")); }

    public AlmoxarifadoMovimentacao retirar(AlmoxarifadoMovimentacaoRequest request, String usuario) {
        if (request == null || vazio(request.material()) || request.quantidade() == null || request.quantidade() < 1
                || vazio(request.responsavelRetirada()) || vazio(request.responsavelEntrega()) || request.dataEntrega() == null) {
            throw erro(HttpStatus.BAD_REQUEST, "Preencha material, quantidade, responsáveis e data da retirada.");
        }
        AlmoxarifadoMovimentacao item = new AlmoxarifadoMovimentacao();
        item.setTipo(vazio(request.tipo()) ? "SAIDA" : request.tipo()); item.setMaterial(request.material()); item.setCategoriaMaterial(request.categoriaMaterial());
        item.setQuantidade(request.quantidade()); item.setEvento(request.evento()); item.setEstadoRetirada(request.estadoRetirada());
        item.setResponsavelRetirada(request.responsavelRetirada()); item.setResponsavelEntrega(request.responsavelEntrega()); item.setDataEntrega(request.dataEntrega());
        item.setPrevisaoDevolucao(request.previsaoDevolucao()); item.setObservacoesRetirada(request.observacoesRetirada()); item.setStatus("EM_USO");
        item.setCriadoPor(usuario); item.setCriadoEm(Instant.now());
        return repository.save(item);
    }

    @Transactional
    public AlmoxarifadoMovimentacao devolver(Long id, AlmoxarifadoDevolucaoRequest request) {
        AlmoxarifadoMovimentacao item = buscar(id);
        if (request == null || request.versao() == null || !request.versao().equals(item.getVersao())) throw erro(HttpStatus.CONFLICT, "A movimentação foi alterada por outro operador. Recarregue os dados.");
        if (item.getStatus().equals("DEVOLVIDO")) throw erro(HttpStatus.CONFLICT, "Esta movimentação já foi devolvida.");
        int devolvido = devolucaoRepository.findByMovimentacaoIdOrderByDataDevolucaoAscIdAsc(id).stream().mapToInt(d -> d.getQuantidade()).sum();
        int saldo = item.getQuantidade() - devolvido;
        if (request.quantidadeDevolvida() == null || request.quantidadeDevolvida() < 1 || request.quantidadeDevolvida() > saldo
                || request.dataDevolucao() == null || vazio(request.responsavelRecebimento())) throw erro(HttpStatus.BAD_REQUEST, "Preencha quantidade, data e responsável pelo recebimento.");
        var devolucao = new br.gov.betim.esporte.model.AlmoxarifadoDevolucao(); devolucao.setMovimentacao(item); devolucao.setQuantidade(request.quantidadeDevolvida()); devolucao.setDataDevolucao(request.dataDevolucao()); devolucao.setEstado(request.estadoDevolucao()); devolucao.setResponsavelRecebimento(request.responsavelRecebimento()); devolucao.setObservacoes(request.observacoesDevolucao()); devolucaoRepository.save(devolucao);
        item.setQuantidadeDevolvida(devolvido + request.quantidadeDevolvida()); item.setDataDevolucao(request.dataDevolucao()); item.setEstadoDevolucao(request.estadoDevolucao());
        item.setResponsavelRecebimento(request.responsavelRecebimento()); item.setObservacoesDevolucao(request.observacoesDevolucao());
        item.setStatus(item.getQuantidadeDevolvida().equals(item.getQuantidade()) ? "DEVOLVIDO" : "DEVOLUCAO_PARCIAL");
        return repository.save(item);
    }

    public void definirFoto(AlmoxarifadoMovimentacao item, String caminho, boolean devolucao) { if (devolucao) item.setFotoDevolucao(caminho); else item.setFotoRetirada(caminho); repository.save(item); }
    private static boolean vazio(String valor) { return valor == null || valor.isBlank(); }
    private static ResponseStatusException erro(HttpStatus status, String mensagem) { return new ResponseStatusException(status, mensagem); }
}
