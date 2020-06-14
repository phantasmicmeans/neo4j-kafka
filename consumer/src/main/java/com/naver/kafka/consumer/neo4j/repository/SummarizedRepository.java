package com.naver.kafka.consumer.neo4j.repository;

import com.naver.kafka.consumer.model.Summarized;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface SummarizedRepository extends Neo4jRepository<Summarized, Long> {

    @Query("MATCH (a:Article)\n" +
            "WHERE a.officeId=$oid AND a.articleId=$aid\n" +
            "CREATE (a)-[:SUMMARY]->(s:Summarized $summarized)")
    void saveSummarizedResult(@Param("oid") String oid, @Param("aid") String aid, @Param("summarized") Summarized summarized);

}
