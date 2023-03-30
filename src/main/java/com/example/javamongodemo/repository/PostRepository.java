package com.example.javamongodemo.repository;

import com.example.javamongodemo.data.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String>, PostCustomizedRepository{
    List<Post> findPostByTitleIn(String... title);

    List<Post> findPostByAgeGreaterThan(int age);

    long countByAge(int age);

}
