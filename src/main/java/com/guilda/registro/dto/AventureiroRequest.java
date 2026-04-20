package com.guilda.registro.dto;

import com.guilda.registro.domain.enums.ClasseAventureiro;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AventureiroRequest(
        @NotBlank(message = "O nome não pode ser vazio")
        String nome,

        @NotNull(message = "A classe é obrigatória")
        ClasseAventureiro classe,

        @NotNull(message = "O nível é obrigatório")
        @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
        Integer nivel
) {}