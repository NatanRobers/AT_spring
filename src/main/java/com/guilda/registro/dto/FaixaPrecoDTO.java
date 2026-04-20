package com.guilda.registro.dto;

import java.math.BigDecimal;

public record FaixaPrecoDTO(BigDecimal de, BigDecimal ate, long quantidade) {}