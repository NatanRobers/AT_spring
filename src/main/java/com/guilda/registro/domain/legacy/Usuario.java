package com.guilda.registro.domain.legacy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Entity
@Table(schema = "audit", name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(name = "senha_hash")
    private String senhaHash;

    private String status;

    @Column(name = "ultimo_login_em", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime ultimoLoginEm;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            schema = "audit",
            name = "user_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


}