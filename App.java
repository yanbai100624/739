package com.example;

import org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class App {
    public static void main(String[] args) {

        // Connect Database
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://yanbai811:100624@cluster0.nazbnfc.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings
                .builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");

        // Create Collection
        MongoCollection<Document> babies = database.getCollection("babies");

        // Create Baby
        Baby baby1 = new Baby("Baby1", 1);
        babies.insertOne(baby1.toDoc());

        // Find Baby
        Document baby1_find = babies.find(Filters.eq("Name", "Baby1")).first();
        System.out.println("Found a baby: " + baby1_find.toString());

        // Update Baby's Age
        baby1_find.put("Age", 4);
        System.out.println("Updated a baby's age: " + baby1_find.toString());

        // Delete Baby
        System.out.println("All babies count: " + babies.countDocuments());
        System.out.println("Deleted One Baby");
        babies.findOneAndDelete(Filters.eq("Name", "Baby1"));
        System.out.println("All babies count: " + babies.countDocuments());

        mongoClient.close();

        /*
            Terminal Result:

            Found a baby: Document{{_id=64211fa607ad461d78d8992f, Name=Baby1, Age=1}}
            Updated a baby's age: Document{{_id=64211fa607ad461d78d8992f, Name=Baby1, Age=4}}
            All babies count: 1
            Deleted One Baby
            All babies count: 0
         */
    }
}
