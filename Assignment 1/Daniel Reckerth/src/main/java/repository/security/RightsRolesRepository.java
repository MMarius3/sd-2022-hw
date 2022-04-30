package repository.security;

import database.enums.RightType;
import database.enums.RoleType;
import model.Right;
import model.Role;
import model.User;

import java.util.List;
import java.util.Optional;

public interface RightsRolesRepository {

  void addRole(String role);

  void addRight(String right);

  Optional<Role> findRoleByTitle(String role);

  Optional<Role> findRoleById(Long id);

  Optional<Right> findRightByTitle(String right);

  Optional<Right> findRightById(Long id);

  void addRolesToUsers(User user, List<Role> roles);

  List<Role> findRolesForUsers(Long id);

  void addRoleRight(Long roleId, Long rightId);
}
