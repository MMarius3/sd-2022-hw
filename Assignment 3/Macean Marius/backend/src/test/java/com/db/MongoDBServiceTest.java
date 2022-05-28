package com.db;

import com.TestCreationFactory;
import com.coin.CoinMapper;
import com.coin.CoinService;
import com.coin.model.Coin;
import com.coin.model.dto.CoinDTO;
import com.coin.model.mongo.CoinMongoDB;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MongoDBServiceTest {

    @InjectMocks
    private MongoDBService mongoDBService;

    @Mock
    private DBCollection test;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mongoDBService = new MongoDBService();
    }

    @Test
    void insertToMongo() {
        CoinMongoDB coinMongoDB = TestCreationFactory.newCoinMongoDB();
        DBObject basicDBObject = new BasicDBObject("pair", coinMongoDB.getPair()).append("top", coinMongoDB.getTop());
        mongoDBService.insertToMongo(coinMongoDB);
        verify(test, times(0)).insert(basicDBObject);
    }
}