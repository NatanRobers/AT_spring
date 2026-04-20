package com.guilda.registro.dto;

import com.guilda.registro.domain.enums.ClasseAventureiro;

public record AventureiroResumoResponse(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        boolean ativo
) {}