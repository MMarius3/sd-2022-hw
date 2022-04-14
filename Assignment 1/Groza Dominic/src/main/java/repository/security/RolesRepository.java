package repository.security;

import model.Role;
import model.User;

import java.util.List;

public interface RolesRepository {

    void addRole(String role);

    Role findRoleByTitle(String role);

    Role findRoleById(Long roleId);

    void addRoleToUser(User user, Role role);

    Role findRoleForUser(Long userId);
}
