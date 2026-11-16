package br.gov.betim.esporte.repository;

import br.gov.betim.esporte.model.AlmoxarifadoDevolucao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlmoxarifadoDevolucaoRepository extends JpaRepository<AlmoxarifadoDevolucao, Long> {
    List<AlmoxarifadoDevolucao> findByMovimentacaoIdOrderByDataDevolucaoAscIdAsc(Long movimentacaoId);
}
