package com.example.bookstore.security.dto;

import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();

}