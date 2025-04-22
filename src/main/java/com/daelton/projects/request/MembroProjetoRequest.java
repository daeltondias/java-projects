
package com.daelton.projects.request;

import jakarta.validation.constraints.NotNull;

public record MembroProjetoRequest(
    @NotNull Long idpessoa,
    @NotNull Long idprojeto) {
}
