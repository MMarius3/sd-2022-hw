package com.core.dto.user;

import com.sun.istack.NotNull;
import com.infrastructure.entity.UserType;

public class UserDto {
    @NotNull
    private int id;

    @NotNull
//    @StringLength(minLimit = 3, maxLimit = 50)
    private String name;

    @NotNull
//    @StringLength(minLimit = 5, maxLimit = 50)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private UserType userType;

    public int getId() {
        return id;
    }

    public UserDto setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public UserDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }
}
