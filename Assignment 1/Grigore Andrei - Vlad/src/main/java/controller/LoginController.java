package controller;

import database.Constants;
import database.JDBConnectionWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.UserValidator;
import repository.account.AccountRepositoryMySQL;
import repository.action.ActionRepository;
import repository.action.ActionRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.*;
import view.*;

import java.sql.Connection;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;

public class LoginController {
    private final LoginViewFX loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private final Stage stage;

    private AdminView adminView;
    private EmployeeView employeeView;
    private Scene loginScene;
    private Scene adminScene;
    private Scene employeeScene;
    private CreateUserView createUserView;
    private AdminActionService adminActionService;
    private RightsRolesRepository rightsRolesRepository;
    private ActionService actionService;
    private UserRepository userRepository;
    private ActionRepository actionRepository;
    private Connection connection;
    private ClientRepository clientRepository;

    public LoginController(Stage stage, LoginViewFX loginView, AuthenticationService authenticationService, UserValidator userValidator,EmployeeView employeeView) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.employeeView = employeeView;
        this.stage = stage;
        initScene();
        initButtons();
        initServices();
        stage.setScene(loginScene);
        stage.show();
    }


    private void initScene(){
        adminView = new AdminView();
        employeeView = new EmployeeView();
        loginScene = new Scene(loginView,270,250);
        adminScene = new Scene(adminView,800,600);
        employeeScene = new Scene(employeeView,800,600);
    }

    private void initServices(){
        connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        actionRepository = new ActionRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        clientRepository = new ClientRepositoryMySQL(connection);
        this.actionService = new ActionServiceMySQL(connection,actionRepository);
        this.adminActionService = new AdminActionServiceMySQL(userRepository,rightsRolesRepository,actionRepository);
    }

    private void initButtons(){
        this.loginView.getLogin().setOnAction(actionEvent -> {
            String username = loginView.getUsername().getText();
            String password = loginView.getPassword().getText();

            User user =authenticationService.login(username, password);
            if(user != null) {
                if (user.getRole().getRole().equals(Constants.Roles.ADMINISTRATOR)) {
                    AdminController adminController = new AdminController(user, stage, adminView, adminScene, createUserView, adminActionService, userRepository, actionRepository, userValidator);
                } else if (user.getRole().getRole().equals(Constants.Roles.EMPLOYEE)) {
                    EmployeeController employeeController = new EmployeeController(user,employeeView,employeeScene, new AccountView(), new CreateClientView(), actionService, new CreateAccountView(), clientRepository, new AccountRepositoryMySQL(connection),stage, new ClientValidator(clientRepository), new AccountValidator());
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Wrong email or password", ButtonType.OK);
                alert.showAndWait();
            }
        });

        this.loginView.getRegister().setOnAction(actionEvent -> {
            String username = loginView.getUsername().getText();
            String password = loginView.getPassword().getText();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                authenticationService.register(username, password);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,userValidator.getFormattedErrors(), ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
}
