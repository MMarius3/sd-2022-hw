package com.db;

import com.coin.model.mongo.CoinMongoDB;
import com.mongodb.*;
import org.springframework.stereotype.Service;

@Service
public class MongoDBService {
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection test;
    public MongoDBService() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("MongoDBCryptoExchange");
        test = database.getCollection("test");
    }
    private static DBObject convert(CoinMongoDB coinMongoDB) {
        System.out.println("convert");
        return new BasicDBObject("pair", coinMongoDB.getPair()).append("top", coinMongoDB.getTop());
    }
    public void insertToMongo(CoinMongoDB coinMongoDB) {
        System.out.println("insertToMongo");
        test.insert(convert(coinMongoDB));
    }
}
