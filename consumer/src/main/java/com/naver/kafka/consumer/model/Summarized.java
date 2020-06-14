package com.naver.kafka.consumer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.naver.kafka.consumer.neo4j.relationship.Summary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Summarized {
    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("error_msg") private String errorMsg;
    @JsonProperty("result_code") private String resultCode;
    @JsonProperty("summary") private String summary;

    @Relationship(type = "SUMMARY", direction = INCOMING)
    private Summary summarized;
}
