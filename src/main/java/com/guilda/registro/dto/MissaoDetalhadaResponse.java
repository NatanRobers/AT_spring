package com.guilda.registro.dto;

import com.guilda.registro.domain.enums.NivelPerigo;
import com.guilda.registro.domain.enums.StatusMissao;
import java.time.OffsetDateTime;
import java.util.List;

public record MissaoDetalhadaResponse(
        Long id,
        String titulo,
        NivelPerigo nivelPerigo,
        StatusMissao status,
        OffsetDateTime dataInicio,
        OffsetDateTime dataTermino,
        List<ParticipacaoResponse> participantes
) {}