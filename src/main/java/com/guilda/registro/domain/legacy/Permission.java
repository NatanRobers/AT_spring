package com.guilda.registro.domain.legacy;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "audit", name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String descricao;
}