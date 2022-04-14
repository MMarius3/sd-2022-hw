package Controller;

import Database.JDBConnectionWrapper;
import Model.Role;
import Model.User;
import Model.Validator.ClientAccountValidator;
import Model.Validator.ClientValidator;
import Model.Validator.UserValidator;
import Repository.Action.ActionRepositoryMySQL;
import Repository.Client.ClientRepositoryMySQL;
import Repository.ClientAccount.ClientAccountRepositoryMySQL;
import Repository.User.UserRepositoryMySQL;
import Service.User.AdminServiceMySQL;
import Service.User.AuthenticationServiceMySQL;
import Service.User.RegularUserServiceMySQL;
import View.AdminView;
import View.EmployeeView;
import View.LoginView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.List;

import static Database.Constants.Roles.EMPLOYEE;
import static Database.Constants.Schemas.PRODUCTION;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationServiceMySQL authenticationService;
    private Stage stage;
    private final UserValidator userValidator;

    public LoginController(LoginView loginView, AuthenticationServiceMySQL authenticationService, Stage stage, UserValidator userValidator){
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.stage = stage;
        this.userValidator = userValidator;
        this.loginView.setVisible(true);

        loginView.getLogin().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = loginView.getUsername().getText();
                String password = loginView.getPassword().getText();
                User u = authenticationService.login(username,password);
                Role role = authenticationService.getRightsRolesRepository().findRolesForUser(u.getId());
                System.out.println(role.getRole());
                Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
                if(role.getRole().equals(EMPLOYEE)){
                    ClientRepositoryMySQL c = new ClientRepositoryMySQL(connection);
                    ClientAccountRepositoryMySQL ca = new ClientAccountRepositoryMySQL(connection);
                    RegularUserServiceMySQL reg = new RegularUserServiceMySQL(c,ca);
                    ActionRepositoryMySQL action = new ActionRepositoryMySQL(connection);
                    loginView.setVisible(false);
                    EmployeeView employeeView = new EmployeeView();
                    EmployeeController employeeController = new EmployeeController(employeeView,reg,action,u, new ClientValidator(c), new ClientAccountValidator(ca));
                    stage.setScene(new Scene(employeeView));
                }else{
                    UserRepositoryMySQL userRepositoryMySQL = new UserRepositoryMySQL(connection,authenticationService.getRightsRolesRepository());
                    AdminView adminView = new AdminView();
                    AdminServiceMySQL adminServiceMySQL = new AdminServiceMySQL(userRepositoryMySQL);
                    AdminController adminController = new AdminController(adminView, adminServiceMySQL,authenticationService.getRightsRolesRepository(), new UserValidator(userRepositoryMySQL));
                    stage.setScene(new Scene(adminView));
                }
                System.out.println(u.getUsername() + " authenticated");
            }
        });

        loginView.getRegister().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                userValidator.validate(loginView.getUsername().getText(),loginView.getPassword().getText());
                final List<String> errors = userValidator.getErrors();
                if (errors.isEmpty()) {
                    authenticationService.register(loginView.getUsername().getText(), loginView.getPassword().getText());
                    System.out.println("done");
                } else {
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setContentText(userValidator.getFormattedErrors());
                    error.show();
                }
            }
        });
    }
}
