package com.lab4.demo.user.dto;

import com.lab4.demo.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @Email(message = "Email is not valid")
    @NotNull(message = "Email is required")
    private String email;
    @Size(min = 6 , message = "Password must be at least 6 characters long")
    @Value("noedit")
    private String password;
    @NotNull
    @Size(min = 4 , message = "Username must be at least 4 characters long")
    private String username;
    private String role;
}
