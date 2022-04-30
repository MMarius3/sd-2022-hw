package com.core.dto.security;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {

    private String token;
    private int id;
    private String username;
    private String email;
}