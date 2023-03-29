package com.example.javamongodemo.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseRepositoryImpl<T> implements BaseRepository{

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Autowired
    private final MongoClient mongoClient;

    protected BaseRepositoryImpl(MongoTemplate mongoTemplate, MongoClient mongoClient) {
        this.mongoTemplate = mongoTemplate;
        this.mongoClient = mongoClient;
    }

    private MongoCollection<Document> getMongoCollection(){
        return mongoClient.getDatabase(getDatabaseName()).getCollection(getCollectionName());
    }

    public abstract String getCollectionName();

    public abstract String getDatabaseName();

    public abstract T convertDocumentToObject(Document document);

    @Override
    public void findAllQuery() {
        printAllDocument(getMongoCollection().find());
    }

    @Override
    public void insertOne(Document document) {
        getMongoCollection().insertOne(document);
    }

    @Override
    public void insertMany(List<Document> documents) {
        getMongoCollection().insertMany(documents);
    }

    @Override
    public void unsetField(Document query,String fieldName) {
        Document update = new Document("$unset", new Document(fieldName,""));
        updateOne(query,update,new UpdateOptions().upsert(false));
    }

    @Override
    public MongoCursor<Document> findWithQuery(Document document, Document projectionField, String sortField) {
        FindIterable<Document> query = getMongoCollection().find(document);
        if(Objects.nonNull(projectionField)){
            query.projection(projectionField);
        }
        if(Objects.nonNull(sortField)){
            query.sort(Sorts.ascending(sortField));
        }
        return query.cursor();
    }

    @Override
    public void deleteCollection() {
        getMongoCollection().drop();
    }

    @Override
    public void updateOne(Document query, Document update, UpdateOptions updateOptions) {
        UpdateResult result = getMongoCollection().updateOne(query, update, updateOptions);
        System.out.println("Modified document count: " + result.getModifiedCount());
    }

    private void printAllDocument(FindIterable<Document> documents){
        for (Document document : documents) {
            printDocument(document);
        }
    }

    private void printDocument(Document doc){
        if (doc == null) {
            System.out.println("No results found.");
        } else {
            System.out.println(doc.toJson());
        }
    }

    @Override
    public Document findOne(Document query,Document projectionField,String sortField) {
        FindIterable<Document> result = getMongoCollection().find(query);
        if(Objects.nonNull(projectionField)){
            result.projection(projectionField);
        }
        if(Objects.nonNull(sortField)){
            result.sort(Sorts.ascending(sortField));
        }
        return result.first();
    }

    public List<T> castFromListDBObject(MongoCursor<Document> cursor) {
        List<T> result = new ArrayList<>();
        while (cursor.hasNext()) {
            try {
                T e = convertDocumentToObject(cursor.next());
                result.add(e);
            } catch (Exception ex) {
                continue;
            }
        }
        return result;
    }

}
