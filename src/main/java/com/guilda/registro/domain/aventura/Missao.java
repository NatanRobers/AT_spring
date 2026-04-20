package com.guilda.registro.domain.aventura;


import com.guilda.registro.domain.enums.NivelPerigo;
import com.guilda.registro.domain.enums.StatusMissao;
import com.guilda.registro.domain.legacy.Organizacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "aventura", name = "missoes")
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @OneToMany(mappedBy = "missao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipacaoMissao> participacoes = new ArrayList<>();

    @Column(nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo", nullable = false)
    private NivelPerigo nivelPerigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMissao status;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio;

    @Column(name = "data_termino")
    private OffsetDateTime dataTermino;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}