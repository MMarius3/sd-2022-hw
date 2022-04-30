import controller.LoginController;
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
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;
import static javafx.application.Application.launch;

public class Main{

    public static void main(String[] args) {

        Application.launch(Launcher.class, args);

        Launcher launcher = new Launcher();
    }
}




