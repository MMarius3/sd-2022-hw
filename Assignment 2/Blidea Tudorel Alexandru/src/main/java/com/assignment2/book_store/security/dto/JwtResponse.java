package com.assignment2.book_store.security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {

    private Long id;
    private String token;
    private String username;
    private String email;
    private List<String> roles;

}
