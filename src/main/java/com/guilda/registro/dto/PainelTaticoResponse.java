package com.guilda.registro.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PainelTaticoResponse(
        Long missaoId,
        String titulo,
        String status,
        String nivelPerigo,
        Long totalParticipantes,
        Double nivelMedioEquipe,
        BigDecimal totalRecompensa,
        Long totalMvps,
        Long participantesComCompanheiro,
        OffsetDateTime ultimaAtualizacao,
        Double indiceProntidao
) {}