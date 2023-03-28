package com.example.javamongodemo.config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    private final ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);

    @Bean
    public MongoClient mongoClientDriverConnection() {

    /*
    TODO : use CodecRegistry to auto map document with object
            CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(codecRegistry)
                    .build();
    */

        return MongoClients.create(connectionString);
    }
}
