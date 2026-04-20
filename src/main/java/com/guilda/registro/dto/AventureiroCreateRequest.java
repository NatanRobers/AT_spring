package com.guilda.registro.dto;

import jakarta.validation.constraints.NotNull;

public record AventureiroCreateRequest(
        @NotNull(message = "A organização é obrigatória")
        Long organizacaoId,

        @NotNull(message = "O usuário responsável é obrigatório")
        Long usuarioResponsavelId,

        @NotNull(message = "Dados do aventureiro são obrigatórios")
        AventureiroRequest aventureiro
) {}

