package com.guilda.registro.repository;

import com.guilda.registro.domain.aventura.Aventureiro;
import com.guilda.registro.domain.enums.ClasseAventureiro;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface AventureiroSpecifications {

    public static Specification<Aventureiro> comFiltros(Boolean ativo, ClasseAventureiro classe, Integer nivelMinimo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (ativo != null) {
                predicates.add(criteriaBuilder.equal(root.get("ativo"), ativo));
            }
            if (classe != null) {
                predicates.add(criteriaBuilder.equal(root.get("classe"), classe));
            }
            if (nivelMinimo != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("nivel"), nivelMinimo));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}