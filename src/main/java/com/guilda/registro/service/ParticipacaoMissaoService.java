package com.guilda.registro.service;

import com.guilda.registro.domain.aventura.Aventureiro;
import com.guilda.registro.domain.aventura.Missao;
import com.guilda.registro.domain.aventura.ParticipacaoId;
import com.guilda.registro.domain.aventura.ParticipacaoMissao;
import com.guilda.registro.domain.enums.PapelMissao;
import com.guilda.registro.domain.enums.StatusMissao;
import com.guilda.registro.repository.AventureiroRepository;
import com.guilda.registro.repository.MissaoRepository;
import com.guilda.registro.repository.ParticipacaoMissaoRepository;
import com.guilda.registro.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ParticipacaoMissaoService {

    private final MissaoRepository missaoRepository;
    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoMissaoRepository participacaoRepository;

    public ParticipacaoMissaoService(
            MissaoRepository missaoRepository,
            AventureiroRepository aventureiroRepository,
            ParticipacaoMissaoRepository participacaoRepository
    ) {
        this.missaoRepository = missaoRepository;
        this.aventureiroRepository = aventureiroRepository;
        this.participacaoRepository = participacaoRepository;
    }

    @Transactional
    public ParticipacaoMissao registrar(Long missaoId, Long aventureiroId, PapelMissao papel) {

        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada."));

        Aventureiro aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado."));

        if (Boolean.FALSE.equals(aventureiro.getAtivo())) {
            throw new IllegalArgumentException("Aventureiros inativos não podem participar.");
        }

        if (missao.getStatus() == StatusMissao.CONCLUIDA ||
            missao.getStatus() == StatusMissao.CANCELADA) {
            throw new IllegalArgumentException("Missão não aceita participantes.");
        }

        if (!missao.getOrganizacao().getId()
                .equals(aventureiro.getOrganizacao().getId())) {
            throw new IllegalArgumentException("Organizações incompatíveis.");
        }

        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setId(new ParticipacaoId(missao.getId(), aventureiro.getId()));
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapelMissao(papel);
        participacao.setDestaque(false);
        participacao.setRecompensaOuro(BigDecimal.ZERO);

        return participacaoRepository.save(participacao);
    }
}