package com.naver.kafka.consumer.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void init() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T convert(String data, Class<T> type) {
        try {
            return mapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> void print(T data) {
        try {
            log.info(mapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
