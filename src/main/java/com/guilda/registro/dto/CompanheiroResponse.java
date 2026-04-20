package com.guilda.registro.dto;

import com.guilda.registro.domain.enums.EspecieCompanheiro;

public record CompanheiroResponse(
        String nome,
        EspecieCompanheiro especie,
        Integer lealdade
) {}