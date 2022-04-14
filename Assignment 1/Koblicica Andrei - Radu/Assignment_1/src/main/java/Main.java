import controller.LogInController;
import database.Boostrap;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.TransactionValidator;
import model.validator.UserValidator;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImplementation;
import service.client.ClientService;
import service.client.ClientServiceImplementation;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceImplementation;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {


  @Override
  public void start(Stage primaryStage) throws Exception{
    final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

    final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    final ClientRepository clientRepository=new ClientRepositoryMySQL(connection);
    final AccountRepository accountRepository=new AccountRepositoryMySQL(connection);

    final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
            rightsRolesRepository,connection);
    final ClientService clientService=new ClientServiceImplementation(clientRepository);
    final AccountService accountService=new AccountServiceImplementation(accountRepository);
    final UserService userService=new UserServiceImplementation(userRepository);


    final UserValidator userValidator = new UserValidator(userRepository);
    final ClientValidator clientValidator=new ClientValidator(clientRepository);
    final AccountValidator accountValidator=new AccountValidator(accountRepository);
    final TransactionValidator transactionValidator=new TransactionValidator(accountRepository);



    FXMLLoader loader=new FXMLLoader(getClass().getResource("/LogIn.fxml"));
    Parent root = loader.load();
    LogInController logInController=loader.getController();
    logInController.initializeScene(authenticationService, userService, clientService, accountService, userValidator, clientValidator, accountValidator, transactionValidator);
    primaryStage.setTitle("Bank");
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.setResizable(false);
    primaryStage.show();
  }




  public static void main(String[] args) {
    launch(args);
  }
}
