package com.naver.kafka.producer.fetcher.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {
    private String officeId;
    private String articleId;
    private String content;
    private String section;
    private String title;
}