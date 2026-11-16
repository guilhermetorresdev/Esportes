package br.gov.betim.esporte.dto;

import java.time.LocalDate;

public record EventoRequest(
        String nome,
        String categoria,
        String descricao,
        LocalDate dataEvento,
        String hora,
        String local,
        String responsavel,
        String materiais
) {
}
