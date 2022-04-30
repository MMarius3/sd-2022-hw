package launcher;

import controller.LogInController;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.io.IOException;
import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws IOException {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        final UserValidator userValidator = new UserValidator(userRepository);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogIn.fxml"));
        LogInController controller = new LogInController(authenticationService, userValidator, rightsRolesRepository);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle("Welcome!");
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }
}

