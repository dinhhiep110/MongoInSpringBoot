package com.example.javamongodemo;

import com.example.javamongodemo.data.Author;
import com.example.javamongodemo.data.Post;
import com.example.javamongodemo.repository.PostRepository;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JavaMongoDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JavaMongoDemoApplication.class, args);
        PostRepository  postRepository = context.getBean(PostRepository.class);
        Document query = new Document();
        query.append("author.firstName" , "Hiep");
        Document document = postRepository.findOne(query, null, "_id");
        Document authorDoc = (Document) document.get("author");
        Author author = Author.builder()
                .firstName(authorDoc.getString("firstName"))
                .lastName(authorDoc.getString("lastName"))
                .build();

        Post post = Post.builder()
                .id(document.getObjectId("_id").toString())
                .title(document.getString("title"))
                .author(List.of(author))
                .language(document.getList("language",String.class))
                .age(document.getInteger("age"))
                .build();
        System.out.println(post.toString());
        System.out.println();

        Document queryLanguageExists = new Document("language", new Document("$exists", true));
        try(MongoCursor<Document> mongoCursor = postRepository.findWithQuery(queryLanguageExists, null, "_id")){
            List<Post> result = new ArrayList<>();
            while (mongoCursor.hasNext()) {

                Post p = postRepository.convertDocumentToObject(mongoCursor.next());
                result.add(p);
            }

            postRepository.castFromListDBObject(mongoCursor).forEach(po -> {
                System.out.println(po.toString());
            });
        }
    }

}
