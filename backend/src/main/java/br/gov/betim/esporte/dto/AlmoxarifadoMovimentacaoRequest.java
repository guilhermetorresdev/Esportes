package br.gov.betim.esporte.dto;

import java.time.LocalDate;

public record AlmoxarifadoMovimentacaoRequest(String tipo, String material, String categoriaMaterial, Integer quantidade,
        String evento, String estadoRetirada, String responsavelRetirada, String responsavelEntrega,
        LocalDate dataEntrega, LocalDate previsaoDevolucao, String observacoesRetirada) { }
