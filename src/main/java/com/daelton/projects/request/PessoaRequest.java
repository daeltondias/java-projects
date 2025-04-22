
package com.daelton.projects.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PessoaRequest(
    @NotBlank String nome,
    @NotNull LocalDate datanascimento,
    @NotBlank String cpf,
    @NotNull boolean funcionario,
    @NotNull boolean gerente) {
}
