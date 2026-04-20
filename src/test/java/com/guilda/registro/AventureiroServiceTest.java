package com.guilda.registro;

import com.guilda.registro.repository.AventureiroRepository;
import com.guilda.registro.service.AventureiroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AventureiroServiceTest {

    @MockBean
    private AventureiroRepository repository;

    @Autowired
    private AventureiroService service;

    @Test
    public void deveProcessarListagemComFiltrosESpecifications() {

        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        Page<?> resultado = service.buscarComFiltros(null, null, null, Pageable.unpaged());
        assertNotNull(resultado);
        verify(repository).findAll(any(Specification.class), any(Pageable.class));

        System.out.println(" Listagem de Aventureiros com filtros validado");
    }
}