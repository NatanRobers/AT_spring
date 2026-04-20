package com.guilda.registro.service;

import com.guilda.registro.domain.aventura.Aventureiro;
import com.guilda.registro.domain.aventura.Companheiro;
import com.guilda.registro.dto.AventureiroResumoResponse;
import com.guilda.registro.domain.enums.ClasseAventureiro;
import com.guilda.registro.dto.AventureiroCreateRequest;
import com.guilda.registro.dto.AventureiroDetalhadoResponse;
import com.guilda.registro.dto.AventureiroRequest;
import com.guilda.registro.dto.CompanheiroRequest;
import com.guilda.registro.mapper.AventureiroMapper;
import com.guilda.registro.repository.AventureiroRepository;
import com.guilda.registro.repository.AventureiroSpecifications;
import com.guilda.registro.repository.OrganizacaoRepository;
import com.guilda.registro.repository.UsuarioRepository;
import com.guilda.registro.exception.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AventureiroService {

    private final AventureiroRepository repository;
    private final AventureiroMapper mapper;
    private final OrganizacaoRepository organizacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public AventureiroService(
            AventureiroRepository repository,
            AventureiroMapper mapper,
            OrganizacaoRepository organizacaoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.organizacaoRepository = organizacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<AventureiroResumoResponse> buscarComFiltros(Boolean ativo, ClasseAventureiro classe, Integer nivelMinimo, Pageable pageable) {
        var spec = AventureiroSpecifications.comFiltros(ativo, classe, nivelMinimo);

        return repository.findAll(spec, pageable).map(mapper::toResumoResponse);
    }

    public Page<AventureiroResumoResponse> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(mapper::toResumoResponse);
    }

    @Transactional
    public AventureiroDetalhadoResponse criar(AventureiroCreateRequest request) {
        var org = organizacaoRepository.findById(request.organizacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Organização não encontrada"));

        var usuario = usuarioRepository.findById(request.usuarioResponsavelId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário responsável não encontrado"));

        AventureiroRequest aReq = request.aventureiro();

        Aventureiro a = new Aventureiro();
        a.setOrganizacao(org);
        a.setUsuarioResponsavel(usuario);
        a.setNome(aReq.nome());
        a.setClasse(aReq.classe());
        a.setNivel(aReq.nivel());
        a.setAtivo(true);

        return mapper.toDetalhadoResponse(repository.save(a));
    }

    @Transactional
    public AventureiroDetalhadoResponse atualizar(Long id, AventureiroRequest request) {
        Aventureiro a = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado"));

        a.setNome(request.nome());
        a.setClasse(request.classe());
        a.setNivel(request.nivel());

        return mapper.toDetalhadoResponse(repository.save(a));
    }

    @Transactional
    public AventureiroDetalhadoResponse recrutar(Long id) {
        Aventureiro a = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado"));
        a.setAtivo(true);
        return mapper.toDetalhadoResponse(repository.save(a));
    }

    @Transactional
    public AventureiroDetalhadoResponse adicionarCompanheiro(Long aventureiroId, CompanheiroRequest request) {
        Aventureiro a = repository.findById(aventureiroId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado"));

        Companheiro c = a.getCompanheiro();
        if (c == null) {
            c = new Companheiro();
            c.setAventureiro(a);
            a.setCompanheiro(c);
        }

        c.setNome(request.nome());
        c.setEspecie(request.especie());
        c.setLealdade(request.lealdade());

        Aventureiro salvo = repository.save(a);
        return mapper.toDetalhadoResponse(salvo);
    }

    @Transactional
    public void removerCompanheiro(Long aventureiroId) {
        Aventureiro a = repository.findById(aventureiroId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado"));
        a.setCompanheiro(null);
        repository.save(a);
    }

    public AventureiroDetalhadoResponse buscarDetalhado(Long id) {
        Aventureiro a = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aventureiro não encontrado"));
        return mapper.toDetalhadoResponse(a);
    }
}