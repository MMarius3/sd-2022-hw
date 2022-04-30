package controller;

import database.Constants;
import model.Role;
import model.User;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.LoginView;
import view.administrator.AdministratorView;
import view.employee.EmployeeView;

import java.util.List;
import java.util.Optional;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;

    private final EmployeeView employeeView;
    private final AdministratorView administratorView;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator, EmployeeView employeeView, AdministratorView administratorView) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.employeeView = employeeView;
        this.administratorView = administratorView;
        loginButtonAction();
        registerButtonAction();
    }


    public void loginButtonAction() {
        loginView.getLoginButton().setOnAction(event -> {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validateLogin(username, password);

            User user = authenticationService.login(username, password);
            List<String> errors = userValidator.getErrors();

            if (errors.isEmpty()) {
                List<Role> userRoles = user.getRoles();
                Optional<Role> userRole = userRoles.stream().filter(role -> role.getRole().equals(Constants.Roles.EMPLOYEE)).findFirst();

                if (userRole.isPresent()) {
                    loginView.clearFields();
                    loginView.setScene(employeeView.getScene());
                } else {
                    loginView.clearFields();
                    loginView.setScene(administratorView.getScene());
                }
            } else {
                System.out.println(userValidator.getFormattedErrors());
            }
        });
    }


    public void registerButtonAction() {
        loginView.getRegisterButton().setOnAction(event -> {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validateRegister(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(username, password);
            } else {
                for (String error : errors) {
                    System.out.println(error);
                }
            }
        });

    }
}
