package com.guilda.registro.repository;

import com.guilda.registro.domain.aventura.ParticipacaoId;
import com.guilda.registro.domain.aventura.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoId> {}

