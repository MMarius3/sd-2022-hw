package com.assignment2.book_store.repository.mongo;

import com.assignment2.book_store.data.entity.mongo.UserHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class UserHistoryRepositoryTest {

    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${mongodb.userHistoryCollectionName}")
    private String userHistoryCollectionName;

    @Test
    public void setInsertion() {
        UserHistory userHistory = new UserHistory();
        userHistory.setChanges(List.of("Created!"));
        userHistory.setFirstName("Tudorel Alexandru");
        userHistory.setLastName("Blidea");
        userHistory.setEmail("tudorelblidea@gmail.com");
        userHistory.setPasswordHash("69bagpl");
        userHistory.setUpdatedBy("tuddi_test");
        userHistory.setUpdatedOn(LocalDateTime.now());

        mongoTemplate.findAll(UserHistory.class, userHistoryCollectionName).forEach(userHistorei -> {
            System.out.println(userHistorei.getFirstName() + " " + userHistorei.getLastName());
            userHistoryRepository.delete(userHistorei);
        });

        userHistoryRepository.findAll().forEach(userHistoryFromDb -> {
            System.out.println(userHistoryFromDb.getId() + ") " + userHistoryFromDb.getFirstName() + " " + userHistoryFromDb.getLastName());
            userHistoryRepository.delete(userHistoryFromDb);
        });

//        Scanner scanner = new Scanner(System.in);
//        scanner.nextInt();
//        scanner.close();

        mongoTemplate.save(userHistory, userHistoryCollectionName);
    }

}