package com.example.javamongodemo.repository;

import com.example.javamongodemo.data.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String>,BaseRepository, PostCustomizedRepository{
    List<Post> findPostByTitleIn(String... title);

    @Query
    List<Post> findPostByAgeGreaterThan(int age);

}
