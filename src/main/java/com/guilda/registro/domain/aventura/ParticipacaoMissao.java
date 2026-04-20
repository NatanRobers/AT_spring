package com.guilda.registro.domain.aventura;

import com.guilda.registro.domain.enums.PapelMissao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "aventura", name = "participacao_missao")
public class ParticipacaoMissao {

    @EmbeddedId
    private ParticipacaoId id;

    @ManyToOne
    @MapsId("missaoId")
    @JoinColumn(name = "missao_id")
    private Missao missao;

    @ManyToOne
    @MapsId("aventureiroId")
    @JoinColumn(name = "aventureiro_id")
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao", nullable = false)
    private PapelMissao papelMissao;

    @Column(name = "recompensa_ouro")
    private BigDecimal recompensaOuro = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean destaque = false;

    @Column(name = "data_registro", updatable = false)
    private OffsetDateTime dataRegistro;

    @PrePersist
    protected void onCreate() {
        dataRegistro = OffsetDateTime.now();
    }
}