package com.guilda.registro.repository;

import com.guilda.registro.domain.aventura.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long>, JpaSpecificationExecutor<Aventureiro> {

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}