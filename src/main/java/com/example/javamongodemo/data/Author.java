package com.example.javamongodemo.data;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {
    private String firstName;
    private String lastName;
}
