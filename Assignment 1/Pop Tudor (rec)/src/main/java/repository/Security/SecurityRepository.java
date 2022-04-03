package repository.Security;

import model.Right;
import model.Role;
import model.User;
import java.util.List;

public interface SecurityRepository {

    //roles
    void addRole(String role);
     Role findRoleByTitle(String role);
    Role findRoleById(Long roleId);
    void addRolesToUser(User user, List<Role> roles);
    List<Role> findRolesForUser(Long userId);

    //rights
    void addRight(String right);
    Right findRightByTitle(String right);
    void addRoleRight(Long roleId, Long rightId);

}
