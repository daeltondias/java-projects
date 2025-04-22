package com.daelton.projects.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjetoResquest(
    @NotBlank String nome,
    @NotNull LocalDate data_inicio,
    @NotNull LocalDate data_previsao_fim,
    String descricao,
    @NotNull @DecimalMin("0.0") BigDecimal orcamento,
    @NotBlank String risco,
    @NotNull Long idgerente) {
}
