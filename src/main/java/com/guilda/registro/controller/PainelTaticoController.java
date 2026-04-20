package com.guilda.registro.controller;

import com.guilda.registro.dto.PainelTaticoResponse;
import com.guilda.registro.service.PainelTaticoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class PainelTaticoController {

    private final PainelTaticoService service;

    public PainelTaticoController(PainelTaticoService service) {
        this.service = service;
    }

    @GetMapping("/top15dias")
    public List<PainelTaticoResponse> getTop15Dias() {
        return service.buscarTopMissoesRecentes();
    }
}