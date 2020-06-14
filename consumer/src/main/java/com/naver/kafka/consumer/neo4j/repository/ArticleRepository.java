package com.naver.kafka.consumer.neo4j.repository;

import com.naver.kafka.consumer.model.Article;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ArticleRepository extends Neo4jRepository<Article, Long> {
}
