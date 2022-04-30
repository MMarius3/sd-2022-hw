package Controller;

import Model.Role;
import Model.User;
import Model.Validator.UserValidator;
import Service.Secutiry.AuthenticationService;
import View.AdminView;
import View.EmployeeView;
import View.LoginView;
import java.util.List;
import java.util.Optional;

import static Database.Constants.Roles.*;

public class LoginController {

    private final LoginView loginView;
    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private User loggedUser;

    public LoginController(User loggedUser, LoginView loginView, AdminView adminView, EmployeeView employeeView, AuthenticationService authenticationService, UserValidator userValidator) {
        this.adminView = adminView;
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.loginView = loginView;
        this.loggedUser = loggedUser;

        loginAction();
        logOutAction();
    }

    public void loginAction() {
        loginView.getLogin().setOnAction(e->{
            loginView.getNoSuchUserMsg().setText("");
            String username = loginView.getUsername().getText();
            String password = loginView.getPassword().getText();

            userValidator.validateLogin(username, password);
            User user = authenticationService.login(username,password);
            loggedUser.setUsername(user.getUsername());
            loggedUser.setPassword(user.getPassword());
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()){
                if(user == null) loginView.getNoSuchUserMsg().setText("Wrong password");
                else {
                    Optional<Role> role = user.getRoles().stream().filter(r -> r.getRole().equals(ADMINISTRATOR)).findFirst();
                    if (role.isPresent()) loginView.changeView(adminView.getMainScene());
                    else loginView.changeView(employeeView.getMainScene());
                }
            }else loginView.getNoSuchUserMsg().setText(userValidator.getFormattedErrors());

            loginView.getUsername().clear();
            loginView.getPassword().clear();
        });
    }

    public void logOutAction(){
        employeeView.getLogOut().setOnAction(e-> loginView.changeView(loginView.getMainScene()));
        adminView.getLogOut().setOnAction(e->loginView.changeView(loginView.getMainScene()));
    }


}