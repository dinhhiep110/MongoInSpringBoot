package com.example.javamongodemo.repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.List;

public interface IBaseRepository {

    void findAll();

    void insertOne(Document document);

    void insertMany(List<Document> documents);

    MongoCursor<Document> findWithQuery(Document document, Document projectionField, String sortField);

    void deleteCollection();

    void updateOne(Document query, Document update, UpdateOptions updateOptions);

    Document findOne(Document query,Document projection,String sortField);

    void unsetField(Document query,String fieldName);
}
