package com.guilda.registro.repository;

import com.guilda.registro.domain.aventura.Missao;
import com.guilda.registro.domain.enums.NivelPerigo;
import com.guilda.registro.domain.enums.StatusMissao;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface MissaoSpecifications {

    public static Specification<Missao> comFiltros(StatusMissao status, NivelPerigo perigo, OffsetDateTime inicio, OffsetDateTime termino) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) predicates.add(criteriaBuilder.equal(root.get("status"), status));
            if (perigo != null) predicates.add(criteriaBuilder.equal(root.get("nivelPerigo"), perigo));
            if (inicio != null) predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataInicio"), inicio));
            if (termino != null) predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataTermino"), termino));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}