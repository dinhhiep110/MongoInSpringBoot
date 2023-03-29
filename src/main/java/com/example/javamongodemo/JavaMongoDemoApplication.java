package com.example.javamongodemo;

import com.example.javamongodemo.data.Post;
import com.example.javamongodemo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@SpringBootApplication()
@EnableMongoRepositories
public class JavaMongoDemoApplication implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PostRepository postRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JavaMongoDemoApplication.class, args);
//        PostRepositoryImpl postRepository = context.getBean(PostRepositoryImpl.class);
//        Document query = new Document();
//        query.append("author.firstName" , "Hiep");
//        Document projection = new Document("title",1);
//        projection.append("language",1);
//        projection.append("author",1);
//        Document document = postRepository.findOne(query, projection, "_id");
//        Document authorDoc = (Document) document.get("author");
//        Author author = Author.builder()
//                .firstName(authorDoc.getString("firstName"))
//                .lastName(authorDoc.getString("lastName"))
//                .build();
//
//        Post post = Post.builder()
////                .id(document.getObjectId("_id").toString())
//                .title(document.getString("title"))
//                .author(author)
//                .language(document.getList("language",String.class))
////                .age(document.getInteger("age"))
//                .build();
//        System.out.println(post);
//        System.out.println();
//
////        Document queryLanguageExists = new Document("language", new Document("$exists", true));
////        try(MongoCursor<Document> mongoCursor = postRepository.findWithQuery(queryLanguageExists, null, "_id")){
////            List<Post> result = new ArrayList<>();
////            while (mongoCursor.hasNext()) {
////
////                Post p = postRepository.convertDocumentToObject(mongoCursor.next());
////                result.add(p);
////            }
////
////            postRepository.castFromListDBObject(mongoCursor).forEach(po -> {
////                System.out.println(po.toString());
////            });
////        }
    }

    @Override
    public void run(String... args) throws Exception {
        Query query = new Query(Criteria.where("title").is("Hehe").and("language").exists(true));
        mongoTemplate.findAll(Post.class);

//        Author author = Author.builder()
//                .firstName("Duy")
//                .lastName("Hiep")
//                .build();
//
//        Post post = Post.builder()
//                .id(new ObjectId().toString())
//                .title("Programing")
//                .language(Arrays.asList("Java","PHP"))
//                .author(author)
//                .age(20)
//                .build();
//
//        mongoTemplate.save(post);

        System.out.println(postRepository.findByAdultAuthor());

        Query deleteQuery = new Query(Criteria.where("title").is("Dump"));
        mongoTemplate.remove(deleteQuery,Post.class);

        Query updateQuery = new Query(Criteria.where("title").is("Hehe").orOperator(Criteria.where("age").is(20)));
        Update update = new Update();
        update.set("age",18);
        mongoTemplate.updateFirst(updateQuery,update,Post.class);

//        printList(postRepository.findPostByTitleIn("Hehe","Dump"));

//        printList(postRepository.findPostByAgeGreaterThan(19));

        printList(postRepository.findAll());
    }

    private void printList(List<Post> posts) {
        posts.forEach(post -> {
            System.out.println(post.toString());
        });
    }
}
