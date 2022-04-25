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

    @NotNull(message = "Username can't be null!")
    @Size(min = 2, max = 30, message = "Username must have the length between 3 and 30!")
    private String name;
}
