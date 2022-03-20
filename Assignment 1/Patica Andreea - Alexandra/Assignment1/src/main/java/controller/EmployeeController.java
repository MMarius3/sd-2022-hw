package controller;

import model.Client;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import service.client.ClientService;
import service.user.UserService;
import view.ClientView;
import view.EmployeeView;

public class EmployeeController {

    private final UserService userService;
    private final EmployeeView employeeView;

    public EmployeeController(UserService userService){
        this.userService = userService;

        this.employeeView = new EmployeeView();
    }

    public void startController(User user){
        employeeView.initializeFields();
        if (user != null){
            setFields(user);
        }
        initializeSaveButtonListener(user);
        employeeView.display();

    }

    private void setFields(User user){
        employeeView.setIdField(user.getId().toString());
        employeeView.setUsernameField(user.getUsername());
        employeeView.setPasswordField(user.getPassword());
        employeeView.setRepeatPasswordField(user.getPassword());

    }

    private void initializeSaveButtonListener(User user){
        employeeView.getSaveButton().setOnAction(event ->{
            User newUser = new UserBuilder()
                    .setUsername(employeeView.getUsernameField().getText())
                    .setPassword(employeeView.getPasswordField().getText())
                    //.setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))    //TODO rights
                    .build();
            if (user == null){
                userService.save(newUser);
            }
            else{
                newUser.setId(user.getId());
                userService.update(newUser);
            }
        });
    }
}
