package com.guilda.registro.repository;

import com.guilda.registro.domain.aventura.PainelTaticoMissao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.OffsetDateTime;
import java.util.List;

public interface PainelTaticoRepository extends JpaRepository<PainelTaticoMissao, Long> {
    List<PainelTaticoMissao> findByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(OffsetDateTime data, Pageable pageable);
}