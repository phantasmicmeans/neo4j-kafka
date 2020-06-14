package com.naver.kafka.consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naver.kafka.consumer.utils.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		JsonUtils.init();
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
