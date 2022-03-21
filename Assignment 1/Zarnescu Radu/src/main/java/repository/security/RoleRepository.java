package repository.security;

import model.Role;
import model.User;

import java.util.List;

public interface RoleRepository {
    void addRole(String role);

    Role findRoleByTitle(String role);

    Role findRoleById(int roleId);

    void addRolesToUser(User user, List<Role> roles);

    List<Role> findRolesForUser(Long userId);

}