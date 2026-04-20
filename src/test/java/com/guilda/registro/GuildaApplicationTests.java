package com.guilda.registro;

import com.guilda.registro.repository.MissaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class GuildaApplicationTests {

    @Autowired
    private MissaoRepository missaoRepository;

    @Test
    void testarRelatorioGerencial() {
        missaoRepository.gerarRelatorioMetricas(null, null, PageRequest.of(0, 10));
    }
}