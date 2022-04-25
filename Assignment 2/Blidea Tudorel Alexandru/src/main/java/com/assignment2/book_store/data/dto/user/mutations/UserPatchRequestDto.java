package com.assignment2.book_store.data.dto.user.mutations;

import com.assignment2.book_store.data.entity.jpa.enums.UserRole;
import com.assignment2.book_store.data.entity.jpa.enums.UserStatus;
import lombok.Data;

@Data
public class UserPatchRequestDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private UserRole userRole;
    private UserStatus userStatus;
    private Double accountBalance;

}
