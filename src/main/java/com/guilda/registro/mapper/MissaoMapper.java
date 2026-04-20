package com.guilda.registro.mapper;

import com.guilda.registro.domain.aventura.Missao;
import com.guilda.registro.domain.aventura.ParticipacaoMissao;
import com.guilda.registro.dto.MissaoDetalhadaResponse;
import com.guilda.registro.dto.ParticipacaoResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MissaoMapper {

    public MissaoDetalhadaResponse toDetalhadaResponse(Missao m) {
        List<ParticipacaoResponse> participantesDTO = m.getParticipacoes() == null ? Collections.emptyList() :
                m.getParticipacoes().stream()
                .map(this::toParticipacaoResponse)
                .collect(Collectors.toList());

        return new MissaoDetalhadaResponse(
                m.getId(), m.getTitulo(), m.getNivelPerigo(), m.getStatus(),
                m.getDataInicio(), m.getDataTermino(), participantesDTO
        );
    }

    private ParticipacaoResponse toParticipacaoResponse(ParticipacaoMissao p) {
        return new ParticipacaoResponse(
                p.getAventureiro().getId(),
                p.getAventureiro().getNome(),
                p.getPapelMissao(),
                p.getRecompensaOuro(),
                p.getDestaque(),
                p.getDataRegistro()
        );
    }
}