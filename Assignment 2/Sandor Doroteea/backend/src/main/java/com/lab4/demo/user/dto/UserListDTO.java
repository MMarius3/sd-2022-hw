package com.lab4.demo.user.dto;

import com.lab4.demo.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserListDTO extends UserMinimalDTO {
    @NotNull(message = "Password is required")
    @Size(
            min = 2,
            max = 20,
            message = "Password must be between 2 and 20 characters long")
    private String password;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    private Set<String> roles;

}