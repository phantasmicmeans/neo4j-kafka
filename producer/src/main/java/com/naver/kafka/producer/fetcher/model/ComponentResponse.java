package com.naver.kafka.producer.fetcher.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComponentResponse<T> {
    private ComponentRequest request;
    private Map<String, Object> value;
    private Map<String, Object> error;
}