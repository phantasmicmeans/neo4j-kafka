package com.naver.kafka.consumer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.naver.kafka.consumer.neo4j.relationship.Summary;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {
    @Id @GeneratedValue private Long id;

    private String officeId;
    private String articleId;
    private String content;
    private String section;
    private String title;

    @Relationship(type = "SUMMARIZED")
    private Summary summarized;
}
