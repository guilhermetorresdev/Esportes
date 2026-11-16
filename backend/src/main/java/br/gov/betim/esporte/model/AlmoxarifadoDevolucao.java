package br.gov.betim.esporte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class AlmoxarifadoDevolucao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) private AlmoxarifadoMovimentacao movimentacao;
    private Integer quantidade;
    private LocalDate dataDevolucao;
    private String estado;
    private String responsavelRecebimento;
    private String observacoes;
    public Long getId() { return id; }
    public AlmoxarifadoMovimentacao getMovimentacao() { return movimentacao; }
    public void setMovimentacao(AlmoxarifadoMovimentacao movimentacao) { this.movimentacao = movimentacao; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getResponsavelRecebimento() { return responsavelRecebimento; }
    public void setResponsavelRecebimento(String responsavelRecebimento) { this.responsavelRecebimento = responsavelRecebimento; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
