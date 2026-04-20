package com.guilda.registro.dto;
import java.math.BigDecimal;

public record ProdutoDTO(String nome, String descricao, String categoria, String raridade, BigDecimal preco) {}