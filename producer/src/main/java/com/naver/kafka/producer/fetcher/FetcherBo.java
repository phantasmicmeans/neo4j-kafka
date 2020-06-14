package com.naver.kafka.producer.fetcher;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naver.kafka.producer.Apps;
import com.naver.kafka.producer.WebClientRepository;
import com.naver.kafka.producer.fetcher.model.Article;
import com.naver.kafka.producer.fetcher.model.ArticleRequest;
import com.naver.kafka.producer.fetcher.model.ComponentRequest;

import com.naver.kafka.producer.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Component
public class FetcherBo {
    private final WebClientRepository webclient;
    private static ObjectMapper mapper = new ObjectMapper();

    public FetcherBo(WebClientRepository clientRepository) {
        this.webclient = clientRepository;
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Mono<String> getArticleInfo(String oid, String aid) {
        ComponentRequest<ArticleRequest> request = createRequest(oid, aid);

        return fetchArticle(request)
                .map(e -> {
                    try {
                        JsonNode node = mapper.readTree(e).get(0);
                        JsonNode value = node.get("value");

                        return value.toString();
                    } catch (JsonProcessingException t) {
                        throw new RuntimeException(t.getMessage());
                    }
                });
    }

    public Mono<String > fetchArticle(ComponentRequest<ArticleRequest> request) {
        return webclient.post(Apps.FETCHER_URL, String.class, request, Duration.ofMillis(5 * 1000));
    }

    private ComponentRequest<ArticleRequest> createRequest(String oid, String aid) {
        ArticleRequest.Data data = new ArticleRequest.Data(oid, aid, false);
        ArticleRequest articleRequest = ArticleRequest.builder()
                .type("ARTICLE")
                .data(data)
                .build();

        return new ComponentRequest<>(UUID.randomUUID().toString(), Collections.singletonList(articleRequest));
    }
}