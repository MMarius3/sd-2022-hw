package com.core.dto;

import com.sun.istack.NotNull;
import com.infrastructure.entity.UserType;
import lombok.Data;

@Data
public class UserDto {
    @NotNull
    public int id;

    @NotNull
//    @StringLength(minLimit = 3, maxLimit = 50)
    public String name;

    @NotNull
//    @StringLength(minLimit = 5, maxLimit = 50)
    public String email;

    @NotNull
    public String password;

    @NotNull
    public UserType userType;
}
