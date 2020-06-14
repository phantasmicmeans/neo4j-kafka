package com.naver.kafka.producer.producer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    private long offset;
    private String topic;
    private int partition;
    private int serializedKeySize;
    private int serializedValueSize;
    private long timestamp;
}