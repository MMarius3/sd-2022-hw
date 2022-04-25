package com.lab4.demo.user.dto;

import com.lab4.demo.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserListDTO extends UserMinimalDTO {

    @NotNull(message = "Password can't be null")
    @Size(min = 8, max = 16)
    private String password;

    @Email(message = "Must be a valid email!")
    @NotNull(message = "Email can't be empty!")
    private String email;

    private Set<String> roles;
}
