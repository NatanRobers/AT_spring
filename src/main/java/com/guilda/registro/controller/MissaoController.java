package com.guilda.registro.controller;

import com.guilda.registro.domain.enums.NivelPerigo;
import com.guilda.registro.domain.enums.StatusMissao;
import com.guilda.registro.dto.MissaoCreateRequest;
import com.guilda.registro.dto.MissaoDetalhadaResponse;
import com.guilda.registro.repository.MissaoRepository;
import com.guilda.registro.service.MissaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService service;

    public MissaoController(MissaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<MissaoDetalhadaResponse>> listar(
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) NivelPerigo perigo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime termino,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<MissaoDetalhadaResponse> pageResult = service.listarMissoes(status, perigo, inicio, termino, PageRequest.of(page, size));
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDetalhadaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/relatorio")
    public ResponseEntity<Page<MissaoRepository.RelatorioMissaoDTO>> relatorio(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime termino,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(service.gerarRelatorioMetricas(inicio, termino, PageRequest.of(page, size,Sort.by("titulo"))));
    }

    @PostMapping
    public ResponseEntity<MissaoDetalhadaResponse> criar(@Valid @RequestBody MissaoCreateRequest request) {
        return ResponseEntity.ok(service.criar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{missaoId}/participacoes/{aventureiroId}")
    public ResponseEntity<Void> removerParticipacao(@PathVariable Long missaoId, @PathVariable Long aventureiroId) {
        service.removerParticipacao(missaoId, aventureiroId);
        return ResponseEntity.noContent().build();
    }
}