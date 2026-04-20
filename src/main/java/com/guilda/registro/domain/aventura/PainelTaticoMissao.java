package com.guilda.registro.domain.aventura;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Immutable
@Table(schema = "operacoes", name = "vw_painel_tatico_missao")
public class PainelTaticoMissao {

    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    private String titulo;
    private String status;

    @Column(name = "nivel_perigo")
    private String nivelPerigo;

    @Column(name = "total_participantes")
    private Long totalParticipantes;

    @Column(name = "nivel_medio_equipe")
    private Double nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private BigDecimal totalRecompensa;

    @Column(name = "total_mvps")
    private Long totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Long participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private OffsetDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private Double indiceProntidao;

    public Long getMissaoId() { return missaoId; }
    public String getTitulo() { return titulo; }
    public String getStatus() { return status; }
    public String getNivelPerigo() { return nivelPerigo; }
    public Long getTotalParticipantes() { return totalParticipantes; }
    public Double getNivelMedioEquipe() { return nivelMedioEquipe; }
    public BigDecimal getTotalRecompensa() { return totalRecompensa; }
    public Long getTotalMvps() { return totalMvps; }
    public Long getParticipantesComCompanheiro() { return participantesComCompanheiro; }
    public OffsetDateTime getUltimaAtualizacao() { return ultimaAtualizacao; }
    public Double getIndiceProntidao() { return indiceProntidao; }
}