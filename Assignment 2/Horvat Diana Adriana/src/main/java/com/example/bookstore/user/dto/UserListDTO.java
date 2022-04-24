package com.example.bookstore.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@SuperBuilder
@AllArgsConstructor
@Builder
public class UserListDTO extends UserMinimalDTO{
    private String email;
    private Set<String> roles;
    private String password;
}
