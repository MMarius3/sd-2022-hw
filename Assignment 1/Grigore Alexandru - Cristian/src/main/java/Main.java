import Controller.AdminController;
import Controller.EmployeeController;
import Controller.LoginController;
import Database.JDBConnectionWrapper;
import Model.Builder.UserBuilder;
import Model.User;
import Model.Validator.UserValidator;
import Repository.Action.ActionRepositoryMySQL;
import Repository.Client.ClientRepositoryMySQL;
import Repository.ClientAccount.ClientAccountRepositoryMySQL;
import Repository.Security.RightRolesRepositoryMySQL;
import Repository.User.UserRepositoryMySQL;
import Service.User.AdminServiceMySQL;
import Service.User.AuthenticationServiceMySQL;
import Service.User.RegularUserServiceMySQL;
import View.AdminView;
import View.EmployeeView;
import View.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import static Database.Constants.Schemas.PRODUCTION;

public class Main extends Application{
    static Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
    final static RightRolesRepositoryMySQL rightRolesRepo = new RightRolesRepositoryMySQL(connection);
    final static UserRepositoryMySQL userRepositoryMySQL = new UserRepositoryMySQL(connection,rightRolesRepo);
    final static AuthenticationServiceMySQL authenticationServiceMySQL = new AuthenticationServiceMySQL(userRepositoryMySQL, rightRolesRepo);
    static AdminView adminView = new AdminView();
    static AdminServiceMySQL adminServiceMySQL = new AdminServiceMySQL(userRepositoryMySQL);
    //static AdminController adminController = new AdminController(adminView, adminServiceMySQL,rightRolesRepo);

    public static void main(String[] args) {

        System.out.println("it worked i guess");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        UserValidator userValidator = new UserValidator(userRepositoryMySQL);
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView, authenticationServiceMySQL,stage,userValidator);
        Scene scene = new Scene(loginView);
        stage.setScene(scene);
        stage.show();
    }
}
