package com.assignment2.book_store.data.dto.user.searchFilters;

import com.assignment2.book_store.data.entity.jpa.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSearchFilter {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private UserRole role;
    private String accountStatus;

}
