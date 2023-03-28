package com.example.javamongodemo.data;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {
    private String id;
    private String title;
    private List<Author> author;
    private List<String> language;
    private int age;
}
