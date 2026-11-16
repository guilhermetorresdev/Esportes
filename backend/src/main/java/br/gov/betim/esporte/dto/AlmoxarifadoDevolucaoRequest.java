package br.gov.betim.esporte.dto;

import java.time.LocalDate;

public record AlmoxarifadoDevolucaoRequest(Long versao, Integer quantidadeDevolvida, LocalDate dataDevolucao,
        String estadoDevolucao, String responsavelRecebimento, String observacoesDevolucao) { }
