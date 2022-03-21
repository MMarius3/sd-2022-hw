package service.user;

import database.Constants;
import model.Action;
import model.Role;
import model.User;
import model.builder.ActionBuilder;
import model.builder.UserBuilder;
import repository.action.ActionRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.sql.SQLException;
import java.util.Arrays;

import static database.Constants.Roles.EMPLOYEE;

public class AdminActionServiceMySQL implements AdminActionService{
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ActionRepository actionRepository;

    public AdminActionServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepositoryMySQL, ActionRepository actionRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepositoryMySQL;
        this.actionRepository = actionRepository;
    }

    @Override
    public Boolean createEmployee(String username, String password) {
        Role role = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        role.setRights(Arrays.asList(Constants.Rights.employeeRights));
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRole(role)
                .build();
        return userRepository.save(user);
    }
    @Override
    public Boolean createAction(User user,String type,String description) throws SQLException {
        Action action = new ActionBuilder()
                .setUser(user)
                .setType(type)
                .setDescription(description)
                .build();
        return actionRepository.save(action);
    }



    @Override
    public Boolean updateEmployee(String change,User user, String newUsername,String newPassword,Role newRole) {
            User userOld = userRepository.findByUsernameAndPassword(user.getUsername());
            userRepository.remove(userOld);
            switch(change){
                case "username" -> user.setUsername(newUsername);
                case "password" -> user.setPassword(newPassword);
                case "role" -> user.setRole(newRole);
            }
            return userRepository.save(user);
    }

    @Override
    public void deleteEmployee(User user) {
            userRepository.remove(user);
    }


}
