package com.naver.kafka.consumer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputContents {
    private String title;
    private String subTitle;
    private String contents;
}
