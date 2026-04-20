package com.guilda.registro;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MarketplaceIntegrationTest {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Test
    public void deveConectarComElasticsearchComSucesso() throws IOException {
        boolean isConnected = elasticsearchClient.ping().value();

        assertTrue(isConnected, "Deve estar rodando no Docker");

        System.out.println("Integrado com Elasticsearch.");
    }
}