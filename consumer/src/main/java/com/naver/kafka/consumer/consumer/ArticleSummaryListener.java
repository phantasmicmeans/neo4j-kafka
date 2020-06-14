package com.naver.kafka.consumer.consumer;

import com.naver.kafka.consumer.Apps;
import com.naver.kafka.consumer.WebClientRepository;
import com.naver.kafka.consumer.config.KafkaConsumerConfig;
import com.naver.kafka.consumer.model.Article;
import com.naver.kafka.consumer.model.InputContents;
import com.naver.kafka.consumer.model.Summarized;
import com.naver.kafka.consumer.neo4j.repository.ArticleRepository;
import com.naver.kafka.consumer.neo4j.repository.SummarizedRepository;
import com.naver.kafka.consumer.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Slf4j
@Service
@AllArgsConstructor
public class ArticleSummaryListener {
    private final ArticleRepository articleRepository;
    private final SummarizedRepository summarizedRepository;
    private final WebClientRepository webclient;
    private final KafkaConsumerConfig config;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> articleSummaryKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, String> consumerConfig = config.consumerFactory("article-news");

        factory.setConsumerFactory(consumerConfig);
        factory.setErrorHandler(new SeekToCurrentErrorHandler());
        return factory;
    }

    @KafkaListener(
            topics = "article-news",
            groupId = "article-summary",
            containerFactory = "articleSummaryKafkaListenerContainerFactory"
    )
    public void listen(@Payload String content) {
        // db에 넣음
        // article-summary topic에 publish 함
        Article topic = JsonUtils.convert(content, Article.class);
        articleRepository.save(topic);
        String oid = topic.getOfficeId();
        String aid = topic.getArticleId();

        InputContents inputContents = createInputContents(oid, aid).block();
        Summarized summarized = summary(inputContents).block();
        summarizedRepository.saveSummarizedResult(oid, aid, summarized);
    }

    private Mono<InputContents> createInputContents(String oid, String aid) {
        return webclient.getMono(String.format(Apps.INPUT_CONTENTS, oid, aid), InputContents.class, Duration.ofMillis(3000));
    }

    private Mono<Summarized> summary(InputContents inputContents) {
        return webclient.post(Apps.SUMMARIZER, Summarized.class, inputContents, Duration.ofMillis(3000));
    }
}
