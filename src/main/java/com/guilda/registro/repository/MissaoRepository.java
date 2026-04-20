package com.guilda.registro.repository;

import com.guilda.registro.domain.aventura.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long>, JpaSpecificationExecutor<Missao> {


    interface RelatorioMissaoDTO {
        String getTitulo();
        String getStatus();
        String getNivelPerigo();
        Long getQuantidadeParticipantes();
        Double getTotalRecompensas();
    }

    @Query("""
             SELECT m.titulo as titulo,
               m.status as status,
               m.nivelPerigo as nivelPerigo,
               COUNT(p) as quantidadeParticipantes,
               COALESCE(SUM(p.recompensaOuro), 0) as totalRecompensas
             FROM Missao m
             LEFT JOIN ParticipacaoMissao p ON m.id = p.missao.id
             WHERE (CAST(:inicio AS timestamp) IS NULL OR m.dataInicio >= :inicio)
             AND   (CAST(:termino AS timestamp) IS NULL OR m.dataTermino <= :termino)
             GROUP BY m.id, m.titulo, m.status, m.nivelPerigo 
           """)

    Page<RelatorioMissaoDTO> gerarRelatorioMetricas(
            @Param("inicio") OffsetDateTime inicio,
            @Param("termino") OffsetDateTime termino,
            Pageable pageable);
}