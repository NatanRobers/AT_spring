package com.guilda.registro.domain.aventura;


import com.guilda.registro.domain.enums.EspecieCompanheiro;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "aventura", name = "companheiros")
public class Companheiro {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "aventureiro_id")
    private Aventureiro aventureiro;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EspecieCompanheiro especie;

    @Column(nullable = false)
    private Integer lealdade;
}