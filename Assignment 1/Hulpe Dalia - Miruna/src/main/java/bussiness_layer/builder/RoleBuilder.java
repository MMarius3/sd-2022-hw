package bussiness_layer.builder;

import bussiness_layer.models.Role;

public class RoleBuilder {
    private Role role;

    public RoleBuilder() {
        role = new Role();
    }

    public RoleBuilder setId(Long id) {
        role.setId(id);
        return this;
    }

    public RoleBuilder setRole(String role) {
        this.role.setRole(role);
        return this;
    }

    public Role build() {
        return role;
    }

}
