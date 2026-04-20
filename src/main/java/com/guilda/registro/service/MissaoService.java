package com.guilda.registro.service;

import com.guilda.registro.domain.aventura.Missao;
import com.guilda.registro.domain.aventura.ParticipacaoId;
import com.guilda.registro.domain.enums.NivelPerigo;
import com.guilda.registro.domain.enums.StatusMissao;
import com.guilda.registro.dto.MissaoCreateRequest;
import com.guilda.registro.dto.MissaoDetalhadaResponse;
import com.guilda.registro.mapper.MissaoMapper;
import com.guilda.registro.repository.MissaoRepository;
import com.guilda.registro.repository.MissaoSpecifications;
import com.guilda.registro.repository.OrganizacaoRepository;
import com.guilda.registro.repository.ParticipacaoMissaoRepository;
import com.guilda.registro.exception.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class MissaoService {

    private final MissaoRepository repository;
    private final MissaoMapper mapper;
    private final OrganizacaoRepository organizacaoRepository;
    private final ParticipacaoMissaoRepository participacaoRepository;

    public MissaoService(
            MissaoRepository repository,
            MissaoMapper mapper,
            OrganizacaoRepository organizacaoRepository,
            ParticipacaoMissaoRepository participacaoRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.organizacaoRepository = organizacaoRepository;
        this.participacaoRepository = participacaoRepository;
    }

    public Page<MissaoDetalhadaResponse> listarMissoes(StatusMissao status, NivelPerigo perigo, OffsetDateTime inicio, OffsetDateTime termino, Pageable pageable) {
        var spec = MissaoSpecifications.comFiltros(status, perigo, inicio, termino);
        return repository.findAll(spec, pageable).map(mapper::toDetalhadaResponse);
    }

    public MissaoDetalhadaResponse buscarPorId(Long id) {
        Missao missao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Missão não encontrada"));
        return mapper.toDetalhadaResponse(missao);
    }

    public Page<MissaoRepository.RelatorioMissaoDTO> gerarRelatorioMetricas(OffsetDateTime inicio, OffsetDateTime termino, Pageable pageable) {
        return repository.gerarRelatorioMetricas(inicio, termino, pageable);
    }

    @Transactional
    public MissaoDetalhadaResponse criar(MissaoCreateRequest request) {
        var org = organizacaoRepository.findById(request.organizacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Organização não encontrada"));

        Missao m = new Missao();
        m.setOrganizacao(org);
        m.setTitulo(request.titulo());
        m.setNivelPerigo(request.nivelPerigo());
        m.setStatus(request.status() == null ? StatusMissao.PLANEJADA : request.status());
        m.setDataInicio(request.dataInicio());
        m.setDataTermino(request.dataTermino());

        Missao salvo = repository.save(m);
        return mapper.toDetalhadaResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Missão não encontrada");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void removerParticipacao(Long missaoId, Long aventureiroId) {
        participacaoRepository.deleteById(new ParticipacaoId(missaoId, aventureiroId));
    }
}