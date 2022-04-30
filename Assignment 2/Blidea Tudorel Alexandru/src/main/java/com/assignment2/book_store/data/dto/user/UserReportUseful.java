package com.assignment2.book_store.data.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserReportUseful {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String role;
    private Double accountBalance;

    public static String getHeaders() {
        return "id, firstName, lastName, username, email, role, accountBalance";
    }

    @Override
    public String toString() {
        return id + ", " + firstName + ", " + lastName + ", " + username + ", " + email + ", " + role + ", " + accountBalance;
    }

}
