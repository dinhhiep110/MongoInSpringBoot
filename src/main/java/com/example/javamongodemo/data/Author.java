package com.example.javamongodemo.data;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {
    @Field("firstName")
    private String firstName;
    @Field("lastName")
    private String lastName;
}
