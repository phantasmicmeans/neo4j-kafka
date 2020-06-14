package com.naver.kafka.consumer.neo4j.relationship;

import com.naver.kafka.consumer.model.Article;
import com.naver.kafka.consumer.model.Summarized;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RelationshipEntity(type = "SUMMARY")
public class Summary {
    @Id @GeneratedValue private Long id;
    private List<String> roles = new ArrayList<>();

    @StartNode
    private Article article;

    @EndNode
    private Summarized summarized;
}
