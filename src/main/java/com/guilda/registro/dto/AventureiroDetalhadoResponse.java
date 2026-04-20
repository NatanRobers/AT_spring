package com.guilda.registro.dto;

import com.guilda.registro.domain.enums.ClasseAventureiro;

public record AventureiroDetalhadoResponse(
        Long id,
        String nome,
        ClasseAventureiro classe,
        Integer nivel,
        boolean ativo,
        CompanheiroResponse companheiro,
        int totalParticipacoes,
        String ultimaMissao
) {}