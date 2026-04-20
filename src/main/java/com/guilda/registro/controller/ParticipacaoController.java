package com.guilda.registro.controller;

import com.guilda.registro.domain.enums.PapelMissao;
import com.guilda.registro.service.ParticipacaoMissaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participacoes")
public class ParticipacaoController {

    private final ParticipacaoMissaoService service;

    public ParticipacaoController(ParticipacaoMissaoService service) {
        this.service = service;
    }

    public record ParticipacaoRequest(
            Long missaoId,
            Long aventureiroId,
            PapelMissao papel
    ) {}

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody ParticipacaoRequest request) {

        service.registrar(
                request.missaoId(),
                request.aventureiroId(),
                request.papel()
        );

        return ResponseEntity.ok("O aventureiro entrou na missão!");
    }
}