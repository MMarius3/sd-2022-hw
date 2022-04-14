package model.builder;

import model.Role;
import model.User;

import java.util.List;

public class UserBuilder {

    private User user;

    //constructor
    public UserBuilder() {
        user = new User();
    }

    public User build() {
        return user;
    }


    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRoles(List<Role> roles) {
        user.setRoles(roles);
        return this;
    }



}

