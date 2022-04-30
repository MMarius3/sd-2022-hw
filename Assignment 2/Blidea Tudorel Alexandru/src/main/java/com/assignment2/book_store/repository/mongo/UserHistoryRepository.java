package com.assignment2.book_store.repository.mongo;

import com.assignment2.book_store.data.entity.mongo.UserHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends MongoRepository<UserHistory, String> {
}
