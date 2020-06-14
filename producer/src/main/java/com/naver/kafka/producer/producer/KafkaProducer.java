package com.naver.kafka.producer.producer;

import com.naver.kafka.producer.producer.model.MetaData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Mono<MetaData> send(String topic, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message).completable();
        return Mono.fromFuture(future)
                .map(SendResult::getRecordMetadata)
                .map(this::convert);
    }

    private MetaData convert(RecordMetadata recordMetadata) {
        return MetaData.builder()
                .offset(recordMetadata.offset())
                .partition(recordMetadata.partition())
                .topic(recordMetadata.topic())
                .timestamp(recordMetadata.timestamp())
                .serializedKeySize(recordMetadata.serializedKeySize())
                .serializedValueSize(recordMetadata.serializedValueSize())
                .build();
    }
}
