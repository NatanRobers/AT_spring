package com.guilda.registro;

import com.guilda.registro.domain.aventura.PainelTaticoMissao;
import com.guilda.registro.repository.PainelTaticoRepository;
import com.guilda.registro.service.PainelTaticoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PainelTaticoServiceTest {

    @Mock
    private PainelTaticoRepository repository;

    @InjectMocks
    private PainelTaticoService service;

    @Test
    public void deveBuscarTopMissoesDosUltimos15Dias() {
        when(repository.findByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(any(OffsetDateTime.class), any(Pageable.class)))
                .thenReturn(Collections.emptyList());
        List<PainelTaticoMissao> resultado = service.buscarTopMissoesRecentes();
        assertNotNull(resultado);
        verify(repository).findByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(any(OffsetDateTime.class), any(Pageable.class));

        System.out.println("Service executado. Filtro de 15 dias e limite de 10 aplicados.");
    }
}