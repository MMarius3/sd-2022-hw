package Repository.Security;

import Model.Right;
import Model.Role;
import Model.User;

import java.util.List;

public interface RightsRolesRepository {

    void addRole(String role);

    void addRight(String right);

    Role findRoleByTitle(String role);

    Role findRoleById(Long roleId);

    Right findRightByTitle(String right);

    void addRolesToUser(User user, Role roles);

    Role findRolesForUser(Long userId);

    void addRoleRight(Long roleId, Long rightId);
}
