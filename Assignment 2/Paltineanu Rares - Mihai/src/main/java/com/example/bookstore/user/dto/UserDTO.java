package com.example.bookstore.user.dto;

import com.example.bookstore.user.model.Role;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String password;
    private Set<Role> roles;
}
