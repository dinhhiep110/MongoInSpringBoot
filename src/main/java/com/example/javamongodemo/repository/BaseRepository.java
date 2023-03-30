package com.example.javamongodemo.repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.List;

public interface BaseRepository {

    void findAllQuery();

    void insertOneDocument(Document document);

    void insertManyDocument(List<Document> documents);

    MongoCursor<Document> findWithQuery(Document document, Document projectionField, String sortField);

    void deleteCollection();

    void updateOneDocument(Document query, Document update, UpdateOptions updateOptions);

    Document findOneDocument(Document query,Document projection,String sortField);

    void unsetField(Document query,String fieldName);
}
