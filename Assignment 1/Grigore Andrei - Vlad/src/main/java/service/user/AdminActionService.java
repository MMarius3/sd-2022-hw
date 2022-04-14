package service.user;

import model.Action;
import model.Role;
import model.User;
import model.builder.ActionBuilder;

import java.sql.SQLException;
import java.util.List;

public interface AdminActionService {
    Boolean createAction(User user,String type,String description) throws SQLException;
    Boolean createEmployee(String username,String password);
    Boolean updateEmployee(String change, User user,  String newUsername, String newPassword, Role newRole);
    void deleteEmployee(User user);
}
