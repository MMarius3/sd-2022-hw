package model.builder;

import model.Role;
import model.User;

import java.util.List;

public class UserBuilder {
    private final User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder setId(Long id) {
        user.setId(id);
        return this;
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

    public User build() {
        return this.user;
    }
}