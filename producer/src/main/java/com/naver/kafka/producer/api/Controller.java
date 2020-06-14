package com.naver.kafka.producer.api;

import com.naver.kafka.producer.fetcher.FetcherBo;

import com.naver.kafka.producer.producer.KafkaProducer;
import com.naver.kafka.producer.producer.model.MetaData;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class Controller {
    private final FetcherBo fetcherBo;
    private final KafkaProducer producer;

    @GetMapping("/produce/article-news/{oid}/{aid}")
    public Mono<MetaData> produceArticle(@PathVariable("oid") String oid, @PathVariable("aid") String aid) {
        return fetcherBo.getArticleInfo(oid, aid)
                .flatMap(e -> producer.send("article-news", e));
    }
}
