package com.assignment2.book_store.security.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class SignupRequest {

    @NotEmpty(message = "first name could not be empty")
    @Size(min = 3, max = 64, message = "first name should have between 3 and 60 characters")
    private String firstName;
    @NotEmpty(message = "last name could not be empty")
    @Size(min = 3, max = 32, message = "last name should have between 3 and 30 characters")
    private String lastName;
    @NotEmpty(message = "username could not be empty")
    @Size(min = 4, max = 16, message = "username should have between 4 and 16 characters")
    private String username;
    @NotEmpty(message = "email could not be empty")
    @Email(message = "email should have the format of address@host.domain")
    @Size(min = 5, max = 64, message = "email should have between 5 and 64 characters")
    private String email;
    @NotEmpty(message = "password could not be empty")
    @Size(min = 8, max = 32, message = "password should have between 8 and 32 characters")
    private String password;

}
