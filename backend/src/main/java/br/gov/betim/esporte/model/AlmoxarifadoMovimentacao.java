package br.gov.betim.esporte.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.time.Instant;
import java.time.LocalDate;

@Entity
public class AlmoxarifadoMovimentacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Version private Long versao;
    private String tipo;
    private String material;
    private String categoriaMaterial;
    private Integer quantidade;
    private String evento;
    private String estadoRetirada;
    private String responsavelRetirada;
    private String responsavelEntrega;
    private LocalDate dataEntrega;
    private LocalDate previsaoDevolucao;
    private String observacoesRetirada;
    private String fotoRetirada;
    private String status;
    private Integer quantidadeDevolvida;
    private LocalDate dataDevolucao;
    private String estadoDevolucao;
    private String responsavelRecebimento;
    private String observacoesDevolucao;
    private String fotoDevolucao;
    private String criadoPor;
    private Instant criadoEm;

    public Long getId() { return id; }
    public Long getVersao() { return versao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getCategoriaMaterial() { return categoriaMaterial; }
    public void setCategoriaMaterial(String categoriaMaterial) { this.categoriaMaterial = categoriaMaterial; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public String getEvento() { return evento; }
    public void setEvento(String evento) { this.evento = evento; }
    public String getEstadoRetirada() { return estadoRetirada; }
    public void setEstadoRetirada(String estadoRetirada) { this.estadoRetirada = estadoRetirada; }
    public String getResponsavelRetirada() { return responsavelRetirada; }
    public void setResponsavelRetirada(String responsavelRetirada) { this.responsavelRetirada = responsavelRetirada; }
    public String getResponsavelEntrega() { return responsavelEntrega; }
    public void setResponsavelEntrega(String responsavelEntrega) { this.responsavelEntrega = responsavelEntrega; }
    public LocalDate getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(LocalDate dataEntrega) { this.dataEntrega = dataEntrega; }
    public LocalDate getPrevisaoDevolucao() { return previsaoDevolucao; }
    public void setPrevisaoDevolucao(LocalDate previsaoDevolucao) { this.previsaoDevolucao = previsaoDevolucao; }
    public String getObservacoesRetirada() { return observacoesRetirada; }
    public void setObservacoesRetirada(String observacoesRetirada) { this.observacoesRetirada = observacoesRetirada; }
    public String getFotoRetirada() { return fotoRetirada; }
    public void setFotoRetirada(String fotoRetirada) { this.fotoRetirada = fotoRetirada; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getQuantidadeDevolvida() { return quantidadeDevolvida; }
    public void setQuantidadeDevolvida(Integer quantidadeDevolvida) { this.quantidadeDevolvida = quantidadeDevolvida; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }
    public String getEstadoDevolucao() { return estadoDevolucao; }
    public void setEstadoDevolucao(String estadoDevolucao) { this.estadoDevolucao = estadoDevolucao; }
    public String getResponsavelRecebimento() { return responsavelRecebimento; }
    public void setResponsavelRecebimento(String responsavelRecebimento) { this.responsavelRecebimento = responsavelRecebimento; }
    public String getObservacoesDevolucao() { return observacoesDevolucao; }
    public void setObservacoesDevolucao(String observacoesDevolucao) { this.observacoesDevolucao = observacoesDevolucao; }
    public String getFotoDevolucao() { return fotoDevolucao; }
    public void setFotoDevolucao(String fotoDevolucao) { this.fotoDevolucao = fotoDevolucao; }
    public String getCriadoPor() { return criadoPor; }
    public void setCriadoPor(String criadoPor) { this.criadoPor = criadoPor; }
    public Instant getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Instant criadoEm) { this.criadoEm = criadoEm; }
}
