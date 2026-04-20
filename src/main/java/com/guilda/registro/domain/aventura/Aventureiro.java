package com.guilda.registro.domain.aventura;

import com.guilda.registro.domain.enums.ClasseAventureiro;
import com.guilda.registro.domain.legacy.Organizacao;
import com.guilda.registro.domain.legacy.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "aventura", name = "aventureiros")
public class Aventureiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_responsavel_id", nullable = false)
    private Usuario usuarioResponsavel;

    @OneToMany(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipacaoMissao> participacoes = new ArrayList<>();

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClasseAventureiro classe;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Companheiro companheiro;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        if (nivel == null || nivel < 1) nivel = 1;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}