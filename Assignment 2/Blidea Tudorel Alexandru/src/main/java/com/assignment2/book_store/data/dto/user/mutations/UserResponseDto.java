package com.assignment2.book_store.data.dto.user.mutations;

import com.assignment2.book_store.data.entity.jpa.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private UserRole role;
    private Double accountBalance;

    public static String getHeaders() {
        return "id, firstName, lastName, username, email, role, accountBalance";
    }

    @Override
    public String toString() {
        return id + ", " + firstName + ", " + lastName + ", " + username + ", " + email + ", " + role + ", " + accountBalance;
    }

}
