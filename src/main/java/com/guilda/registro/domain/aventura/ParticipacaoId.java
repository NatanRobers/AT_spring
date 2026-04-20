package com.guilda.registro.domain.aventura;


import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ParticipacaoId implements Serializable {
    private Long missaoId;
    private Long aventureiroId;
}