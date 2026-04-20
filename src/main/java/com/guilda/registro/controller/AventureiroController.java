package com.guilda.registro.controller;

import com.guilda.registro.dto.AventureiroCreateRequest;
import com.guilda.registro.dto.AventureiroDetalhadoResponse;
import com.guilda.registro.dto.AventureiroRequest;
import com.guilda.registro.dto.AventureiroResumoResponse;
import com.guilda.registro.domain.enums.ClasseAventureiro;
import com.guilda.registro.dto.CompanheiroRequest;
import com.guilda.registro.service.AventureiroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {

    private final AventureiroService service;

    public AventureiroController(AventureiroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<AventureiroResumoResponse>> listar(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) ClasseAventureiro classe,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<AventureiroResumoResponse> response = service.buscarComFiltros(ativo, classe, nivelMinimo, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<AventureiroResumoResponse>> buscarPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by("nome"));
        Page<AventureiroResumoResponse> response = service.buscarPorNome(nome, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroDetalhadoResponse> buscarDetalhado(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarDetalhado(id));
    }

    @PostMapping
    public ResponseEntity<AventureiroDetalhadoResponse> criar(@Valid @RequestBody AventureiroCreateRequest request) {
        return ResponseEntity.ok(service.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AventureiroDetalhadoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AventureiroRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PostMapping("/{id}/recrutar")
    public ResponseEntity<AventureiroDetalhadoResponse> recrutar(@PathVariable Long id) {
        return ResponseEntity.ok(service.recrutar(id));
    }

    @PostMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroDetalhadoResponse> adicionarCompanheiro(
            @PathVariable Long id,
            @Valid @RequestBody CompanheiroRequest request
    ) {
        return ResponseEntity.ok(service.adicionarCompanheiro(id, request));
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        service.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }
}