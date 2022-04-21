package com.lab4.demo.user.dto;

import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String email;
    private String password;
    private Set<Role> roles;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}
