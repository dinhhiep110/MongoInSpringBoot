package com.example.javamongodemo.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "post")
public class Post {
    @Id
    private String id;
    @Field("title")
    private String title;
    @Field("author")
    private Author author;
    @Field("language")
    private List<String> language;
    @Field("age")
    private int age;
}
