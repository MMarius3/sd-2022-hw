package com.lab4.demo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserMinimalDTO {
    private Long id;
    @NotNull
    @Size(
            min = 3,
            max = 20,
            message = "Username must be between 3 and 20 characters long")
    private String name;
}
