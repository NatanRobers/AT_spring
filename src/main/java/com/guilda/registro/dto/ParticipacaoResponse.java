package com.guilda.registro.dto;

import com.guilda.registro.domain.enums.PapelMissao;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ParticipacaoResponse(
        Long idAventureiro,
        String nomeAventureiro,
        PapelMissao papel,
        BigDecimal recompensaOuro,
        Boolean destaque,
        OffsetDateTime dataRegistro
) {}