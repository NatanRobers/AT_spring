package com.guilda.registro.mapper;

import com.guilda.registro.domain.aventura.PainelTaticoMissao;
import com.guilda.registro.dto.PainelTaticoResponse;

public class PainelTaticoMapper {

    public static PainelTaticoResponse toResponse(PainelTaticoMissao p) {
        return new PainelTaticoResponse(
                p.getMissaoId(),
                p.getTitulo(),
                p.getStatus(),
                p.getNivelPerigo(),
                p.getTotalParticipantes(),
                p.getNivelMedioEquipe(),
                p.getTotalRecompensa(),
                p.getTotalMvps(),
                p.getParticipantesComCompanheiro(),
                p.getUltimaAtualizacao(),
                p.getIndiceProntidao()
        );
    }
}