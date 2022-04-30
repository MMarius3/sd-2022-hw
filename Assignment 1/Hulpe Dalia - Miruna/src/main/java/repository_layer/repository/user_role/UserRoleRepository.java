package repository_layer.repository.user_role;

import bussiness_layer.models.User;

import java.util.List;

public interface UserRoleRepository {

  boolean insert (String role, User user);
  User getUserWithRoles (String username, String password);
  User getUserWithRolesUsername (String username);
  List<User> getUsersByRole (String roleName);
}
