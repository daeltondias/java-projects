package com.daelton.projects.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;

public record ProjetoResquestOptional(
    String nome,
    LocalDate data_inicio,
    LocalDate data_previsao_fim,
    String descricao,
    @DecimalMin("0.0") BigDecimal orcamento,
    String risco,
    Long idgerente) {
}
