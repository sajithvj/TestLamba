package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Map;

import static javax.management.Query.eq;

public class MongoLambdaHandler implements RequestHandler<Map<String, Object>, String> {
    private static final String MONGO_URI = null;
    private static MongoClient mongoClient;
    static  {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(MONGO_URI))
                .build();
        // Initialize the MongoDB client
        mongoClient = com.mongodb.client.MongoClients.create(settings);
    }
    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        // Implement your logic here
        MongoDatabase database = mongoClient.getDatabase("demo");
        MongoCollection<org.bson.Document> collection = database.getCollection("books");
        Document document= collection.find().first();
        assert document != null;
        return document.toJson();
    }
}
