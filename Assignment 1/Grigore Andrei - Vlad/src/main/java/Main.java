import controller.AdminController;
import controller.EmployeeController;
import controller.LoginController;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import model.validator.UserValidator;
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

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final UserValidator userValidator = new UserValidator(userRepository);
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        EmployeeView employeeView = new EmployeeView();
        LoginViewFX loginView = new LoginViewFX();
        new LoginController(stage,loginView,authenticationService,userValidator,employeeView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
