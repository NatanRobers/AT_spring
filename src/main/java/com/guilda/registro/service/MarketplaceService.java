package com.guilda.registro.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.guilda.registro.dto.FaixaPrecoDTO;
import com.guilda.registro.dto.PrecoMedioDTO;
import com.guilda.registro.dto.ProdutoDTO;
import com.guilda.registro.dto.TermoContagemDTO;
import com.guilda.registro.marketplace.MarketplaceIndexConstants;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketplaceService {

    private final ElasticsearchClient esClient;

    public MarketplaceService(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    public List<ProdutoDTO> buscaPorNome(String termo) throws IOException {
        return search(s -> s.query(q -> q.match(m -> m.field("nome").query(termo))));
    }

    public List<ProdutoDTO> buscaPorDescricao(String termo) throws IOException {
        return search(s -> s.query(q -> q.match(m -> m.field("descricao").query(termo))));
    }

    public List<ProdutoDTO> buscaFuzzyNome(String termo) throws IOException {
        return search(s -> s.query(q -> q.fuzzy(f -> f.field("nome").value(termo))));
    }

    public List<ProdutoDTO> buscaFraseExataDescricao(String frase) throws IOException {
        return search(s -> s.query(q -> q.matchPhrase(m -> m.field("descricao").query(frase))));
    }

    public List<ProdutoDTO> buscaMultiCampos(String termo) throws IOException {
        return search(s -> s.query(q -> q.multiMatch(m -> m.fields("nome", "descricao").query(termo))));
    }

    public List<ProdutoDTO> buscaComFiltroCategoria(String termo, String categoria) throws IOException {
        return search(s -> s.query(q -> q.bool(b -> b
                .must(m -> m.match(t -> t.field("descricao").query(termo)))
                .filter(f -> f.term(t -> t.field("categoria").value(categoria)))
        )));
    }

    public List<ProdutoDTO> buscaFaixaPreco(BigDecimal min, BigDecimal max) throws IOException {
        return search(s -> s.query(q -> q.range(r -> r.field("preco")
                .gte(co.elastic.clients.json.JsonData.of(min))
                .lte(co.elastic.clients.json.JsonData.of(max))
        )));
    }

    public List<ProdutoDTO> buscaAvancada(String categoria, String raridade, BigDecimal min, BigDecimal max) throws IOException {
        return search(s -> s.query(q -> q.bool(b -> b
                .filter(f -> f.term(t -> t.field("categoria").value(categoria)))
                .filter(f -> f.term(t -> t.field("raridade").value(raridade)))
                .filter(f -> f.range(r -> r.field("preco")
                        .gte(co.elastic.clients.json.JsonData.of(min))
                        .lte(co.elastic.clients.json.JsonData.of(max))))
        )));
    }

    public List<TermoContagemDTO> agregarPorCategoria() throws IOException {
        SearchResponse<Void> res = esClient.search(s -> s.index(MarketplaceIndexConstants.INDEX).size(0)
                        .aggregations("por_categoria", a -> a.terms(t -> t.field("categoria").size(100))),
                Void.class);

        return termosParaDto(res, "por_categoria");
    }

    public List<TermoContagemDTO> agregarPorRaridade() throws IOException {
        SearchResponse<Void> res = esClient.search(s -> s.index(MarketplaceIndexConstants.INDEX).size(0)
                        .aggregations("por_raridade", a -> a.terms(t -> t.field("raridade").size(100))),
                Void.class);

        return termosParaDto(res, "por_raridade");
    }

    public PrecoMedioDTO agregarPrecoMedio() throws IOException {
        SearchResponse<Void> res = esClient.search(s -> s.index(MarketplaceIndexConstants.INDEX).size(0)
                .aggregations("preco_medio", a -> a.avg(avg -> avg.field("preco"))), Void.class);

        var agg = res.aggregations().get("preco_medio");
        BigDecimal valor = (agg != null && agg.isAvg()) ? BigDecimal.valueOf(agg.avg().value()) : null;
        return new PrecoMedioDTO(valor);
    }

    public List<FaixaPrecoDTO> agregarFaixasPreco() throws IOException {
        SearchResponse<Void> res = esClient.search(s -> s
                        .index(MarketplaceIndexConstants.INDEX)
                        .size(0)
                        .aggregations("faixas_preco", a -> a
                                .range(r -> r
                                        .field("preco")
                                        .ranges(
                                                co.elastic.clients.elasticsearch._types.aggregations.AggregationRange.of(rg -> rg.to("100")),
                                                co.elastic.clients.elasticsearch._types.aggregations.AggregationRange.of(rg -> rg.from("100").to("300")),
                                                co.elastic.clients.elasticsearch._types.aggregations.AggregationRange.of(rg -> rg.from("300").to("700")),
                                                co.elastic.clients.elasticsearch._types.aggregations.AggregationRange.of(rg -> rg.from("700"))
                                        )
                                )
                        ),
                Void.class
        );

        var agg = res.aggregations().get("faixas_preco");

        if (agg == null || !agg.isRange()) {
            return List.of();
        }

        return agg.range().buckets().array().stream()
                .map(b -> {
                    Double fromVal = b.from();
                    Double toVal = b.to();

                    BigDecimal from = (fromVal == null || Double.isInfinite(fromVal)) ? null : BigDecimal.valueOf(fromVal);
                    BigDecimal to = (toVal == null || Double.isInfinite(toVal)) ? null : BigDecimal.valueOf(toVal);

                    return new FaixaPrecoDTO(from, to, b.docCount());
                })
                .toList();
    }

    private List<ProdutoDTO> search(java.util.function.Function<co.elastic.clients.elasticsearch.core.SearchRequest.Builder, co.elastic.clients.elasticsearch.core.SearchRequest.Builder> fn) throws IOException {
        SearchResponse<ProdutoDTO> res = esClient.search(s -> fn.apply(s.index(MarketplaceIndexConstants.INDEX)), ProdutoDTO.class);
        return res.hits().hits().stream()
                .filter(hit -> hit.source() != null)
                .map(hit -> hit.source())
                .collect(Collectors.toList());
    }

    private static List<TermoContagemDTO> termosParaDto(SearchResponse<Void> res, String nomeAgg) {
        if (res.aggregations() == null) return List.of();

        Aggregate agg = res.aggregations().get(nomeAgg);
        if (agg == null) return List.of();

        if (agg.isSterms()) {
            return agg.sterms().buckets().array().stream()
                    .map(b -> new TermoContagemDTO(fieldValueToString(b.key()), b.docCount()))
                    .toList();
        }

        if (agg.isLterms()) {
            return agg.lterms().buckets().array().stream()
                    .map(b -> new TermoContagemDTO(String.valueOf(b.key()), b.docCount()))
                    .toList();
        }

        return List.of();
    }

    private static String fieldValueToString(FieldValue v) {
        if (v == null) return "";
        if (v.isString()) return v.stringValue();
        if (v.isLong()) return String.valueOf(v.longValue());
        if (v.isDouble()) return String.valueOf(v.doubleValue());
        if (v.isBoolean()) return String.valueOf(v.booleanValue());
        return v.toString();
    }
}