package com.example.javamongodemo.repository;

import com.example.javamongodemo.data.Post;

import java.util.List;

public interface PostCustomizedRepository {

    List<Post> findByAdultAuthor();
}
