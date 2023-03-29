package com.example.javamongodemo.repository;

import com.example.javamongodemo.data.Post;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PostRepositoryImpl implements PostCustomizedRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Post> findByAdultAuthor() {
        Query andQuery = new Query(Criteria.where("title").is("Hehe").andOperator(Criteria.where("age").is(18)));
        andQuery.fields().include("title","author");
        return mongoTemplate.find(andQuery,Post.class);
    }
}
