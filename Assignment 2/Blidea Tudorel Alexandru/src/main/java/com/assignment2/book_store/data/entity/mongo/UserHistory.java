package com.assignment2.book_store.data.entity.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "user_history")
public class UserHistory {

    @Id
    private String id;

    private Long userDbId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String role;
    private String status;
    private String updatedBy;
    private LocalDateTime updatedOn;
    private List<String> changes;

}
