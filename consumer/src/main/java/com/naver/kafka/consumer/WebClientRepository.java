package com.naver.kafka.consumer;

import com.naver.kafka.consumer.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Slf4j
@Repository
@AllArgsConstructor
public class WebClientRepository {
    private final WebClient webClient;

    public <T> Mono<T> getMono(String url, Class<T> responseType, Duration timeout) {
        URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .single()
                .timeout(timeout)
                .doOnError(e -> log.error(e.getMessage()));
    }

    public <T> Flux<T> getFlux(String url, Class<T> responseType, Duration timeout) {
        URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(responseType)
                .timeout(timeout)
                .doOnError(err -> log.error(err.getMessage()));
    }

    public <U, V> Mono<U> post(String url, Class<U> responseType, V data, Duration timeout) {
        URI uri = UriComponentsBuilder.fromUriString(url).build().toUri();

        return webClient.post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(data))
                .retrieve()
                .bodyToMono(responseType)
                .timeout(timeout)
                .doOnError(err -> log.error(err.getMessage()));
    }
}