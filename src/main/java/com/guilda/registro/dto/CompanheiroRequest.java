package com.guilda.registro.dto;

import com.guilda.registro.domain.enums.EspecieCompanheiro;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanheiroRequest(
        @NotBlank(message = "O nome do companheiro é obrigatório")
        String nome,

        @NotNull(message = "A espécie é obrigatória")
        EspecieCompanheiro especie,

        @NotNull(message = "A lealdade é obrigatória")
        @Min(value = 0, message = "Lealdade mínima é 0")
        @Max(value = 100, message = "Lealdade máxima é 100")
        Integer lealdade
) {}