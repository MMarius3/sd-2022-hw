package com.lab4.demo.user.dto;

import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;

}