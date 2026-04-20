package com.guilda.registro.repository;

import com.guilda.registro.domain.legacy.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {}

