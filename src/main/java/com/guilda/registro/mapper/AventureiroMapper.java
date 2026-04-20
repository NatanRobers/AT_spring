package com.guilda.registro.mapper;

import com.guilda.registro.domain.aventura.Aventureiro;
import com.guilda.registro.domain.aventura.ParticipacaoMissao;
import com.guilda.registro.dto.AventureiroDetalhadoResponse;
import com.guilda.registro.dto.AventureiroResumoResponse;
import com.guilda.registro.dto.CompanheiroResponse;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class AventureiroMapper {

    public AventureiroResumoResponse toResumoResponse(Aventureiro a) {
        return new AventureiroResumoResponse(
                a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo()
        );
    }

    public AventureiroDetalhadoResponse toDetalhadoResponse(Aventureiro a) {
        CompanheiroResponse companheiroResponse = a.getCompanheiro() == null ? null :
                new CompanheiroResponse(a.getCompanheiro().getNome(), a.getCompanheiro().getEspecie(), a.getCompanheiro().getLealdade());;

        int totalParticipacoes = a.getParticipacoes() == null ? 0 : a.getParticipacoes().size();

        String ultimaMissao = null;
        if (a.getParticipacoes() != null && !a.getParticipacoes().isEmpty()) {
            ultimaMissao = a.getParticipacoes().stream()
                    .max(Comparator.comparing(ParticipacaoMissao::getDataRegistro))
                    .map(p -> p.getMissao().getTitulo())
                    .orElse(null);
        }

        return new AventureiroDetalhadoResponse(
                a.getId(), a.getNome(), a.getClasse(), a.getNivel(), a.getAtivo(),
                companheiroResponse, totalParticipacoes, ultimaMissao
        );
    }
}