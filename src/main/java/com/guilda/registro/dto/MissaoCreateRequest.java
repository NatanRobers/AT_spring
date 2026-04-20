package com.guilda.registro.dto;

import com.guilda.registro.domain.enums.NivelPerigo;
import com.guilda.registro.domain.enums.StatusMissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record MissaoCreateRequest(
        @NotNull(message = "A organização é obrigatória")
        Long organizacaoId,

        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotNull(message = "O nível de perigo é obrigatório")
        NivelPerigo nivelPerigo,

        StatusMissao status,

        OffsetDateTime dataInicio,

        OffsetDateTime dataTermino
) {}

