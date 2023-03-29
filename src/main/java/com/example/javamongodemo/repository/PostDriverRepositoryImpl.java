package com.example.javamongodemo.repository;

import com.example.javamongodemo.data.Author;
import com.example.javamongodemo.data.Post;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class PostDriverRepositoryImpl extends BaseRepositoryImpl<Post> {

    private static final String DATABASE_NAME = "MongoDBTest";

    private static final String COLLECTION_NAME = "post";

    protected PostDriverRepositoryImpl(MongoTemplate mongoTemplate, MongoClient mongoClient) {
        super(mongoTemplate, mongoClient);
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    public Post convertDocumentToObject(Document document) {
        Post post = null;
        if (Objects.nonNull(document)) {
            Document authorDoc = (Document) document.get("author");
            Author author = Author.builder().firstName(authorDoc.getString("firstName")).lastName(authorDoc.getString("lastName")).build();

            post = Post.builder().id(document.getObjectId("_id").toString()).title(document.getString("title"))
                    .author(author).language(document.getList("language", String.class)).age(document.getInteger("age")).build();
        }
        return post;
    }

    public void findAllPost() {
        findAllQuery();
    }

    public void findPostWithQueryCondition() {
        Document document = new Document("title", "Hehe");
        Document projectionFields = new Document();
        projectionFields.append("title", 1);
        projectionFields.append("author.lastName", 1);
        findWithQuery(document, projectionFields, "author.firstName");
    }

    public void findOnePost() {
        Document queryOne = new Document();
        queryOne.append("title", "Hehe");

        Document queryTwo = new Document();
        queryTwo.append("$and", Arrays.asList(new Document("title", "Hehe"), new Document("age", new Document("$gte", 19))));

        Document queryThree = new Document();
        queryThree.append("$or", Arrays.asList(new Document("language", new Document("$exists", 1)), new Document("age", new Document("$eq", 20))));

        Document projectionFields = new Document();
        projectionFields.append("title", 1);
        projectionFields.append("author", 1);
        projectionFields.append("language", 1);
        projectionFields.append("age", 1);

        findOne(queryThree, projectionFields, "_id");
    }

    public void updateOnePost() {
        Document query = new Document().append("title", "Hehe");
        Document update = new Document();
        update.append("$set", new Document("age", 19));
        updateOne(query, update, new UpdateOptions().upsert(false));
    }
}
