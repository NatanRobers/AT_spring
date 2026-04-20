package com.guilda.registro.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import com.guilda.registro.dto.ProdutoDTO;
import com.guilda.registro.marketplace.MarketplaceIndexConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class MarketplaceElasticsearchSetup {

    private static final Logger log = LoggerFactory.getLogger(MarketplaceElasticsearchSetup.class);

    private final ElasticsearchClient client;

    public MarketplaceElasticsearchSetup(ElasticsearchClient client) {
        this.client = client;
    }

    @PostConstruct
    public void init() {
        try {
            boolean exists = client.indices().exists(e -> e.index(MarketplaceIndexConstants.INDEX)).value();
            if (!exists) {
                client.indices().create(c -> c
                        .index(MarketplaceIndexConstants.INDEX)
                        .mappings(m -> m
                                .properties("nome", Property.of(p -> p.text(t -> t)))
                                .properties("descricao", Property.of(p -> p.text(t -> t)))
                                .properties("categoria", Property.of(p -> p.text(t -> t.fields("keyword", k -> k.keyword(kk -> kk)))))
                                .properties("raridade", Property.of(p -> p.text(t -> t.fields("keyword", k -> k.keyword(kk -> kk)))))
                                .properties("preco", Property.of(p -> p.scaledFloat(sf -> sf.scalingFactor(100.0))))
                        ));
                log.info("Índice Elasticsearch '{}' criado.", MarketplaceIndexConstants.INDEX);
            }
            seedIfEmpty();
        } catch (Exception e) {
            log.warn("Erro no índice do marketplace no Elasticsearch: {}", e.getMessage());
        }
    }

    private void seedIfEmpty() throws IOException {
        long count = client.count(c -> c.index(MarketplaceIndexConstants.INDEX)).count();
        if (count > 0) {
            return;
        }

        List<ProdutoDTO> samples = List.of(
                new ProdutoDTO("Espada Flamejante", "Arma forjada em fogo dracônico.", "Armas", "EPICA", new BigDecimal("450.00")),
                new ProdutoDTO("Arco Élfico", "Arco leve e preciso.", "Armas", "RARA", new BigDecimal("220.50")),
                new ProdutoDTO("Poção de Cura", "Restaura vitalidade.", "Consumiveis", "COMUM", new BigDecimal("25.00")),
                new ProdutoDTO("Escudo Rúnico", "Proteção reforçada por runas.", "Armaduras", "LENDARIA", new BigDecimal("890.00")),
                new ProdutoDTO("Cajado Arcano", "Amplifica magia arcana.", "Magia", "EPICA", new BigDecimal("600.00"))
        );

        int i = 1;
        for (ProdutoDTO p : samples) {
            String id = String.valueOf(i++);
            client.index(idx -> idx.index(MarketplaceIndexConstants.INDEX).id(id).document(p));
        }
        client.indices().refresh(r -> r.index(MarketplaceIndexConstants.INDEX));
        log.info("Marketplace: {} produtos de exemplo indexados em '{}'.", samples.size(), MarketplaceIndexConstants.INDEX);
    }
}