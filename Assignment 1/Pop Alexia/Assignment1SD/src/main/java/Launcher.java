import Controller.LoginController;
import Database.JDBConnectionWrapper;
import Model.User;
import Model.Validator.AccountValidator;
import Model.Validator.ClientValidator;
import Model.Validator.EventValidator;
import Model.Validator.UserValidator;
import Repository.Account.AccountRepository;
import Repository.Account.AccountRepositoryMySQL;
import Repository.Client.ClientRepository;
import Repository.Client.ClientRepositoryMySQL;
import Repository.Event.EventRepository;
import Repository.Event.EventRepositoryMySQL;
import Repository.Security.RightsRolesRepository;
import Repository.Security.RightsRolesRepositoryMySQL;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import Service.Account.AccountService;
import Service.Account.AccountServiceMySQL;
import Service.Client.ClientService;
import Service.Client.ClientServiceMySQL;
import Service.Event.EventService;
import Service.Event.EventServiceMySQL;
import Service.Secutiry.AuthenticationService;
import Service.Secutiry.AuthenticationServiceMySQL;
import Service.Employee.EmployeeService;
import Service.Employee.EmployeeServiceMySQL;
import View.AdminView;
import View.EmployeeView;
import View.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;
import Controller.EmployeeController;
import Controller.AdminController;

import java.sql.Connection;

import static Database.Constants.Schemas.PRODUCTION;

public class Launcher extends  Application {

    private final LoginView view ;
    public Launcher(){
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        final EmployeeService employeeService = new EmployeeServiceMySQL(userRepository,rightsRolesRepository);
        final ClientService clientService = new ClientServiceMySQL(clientRepository);
        final AccountService accountService = new AccountServiceMySQL(accountRepository);
        final EventRepository eventRepository = new EventRepositoryMySQL(connection);
        final EventService eventService = new EventServiceMySQL(eventRepository);

        final AccountValidator accountValidator = new AccountValidator(accountRepository, clientRepository);
        final ClientValidator clientValidator = new ClientValidator(clientRepository);
        final UserValidator userValidator = new UserValidator(userRepository);
        final EventValidator eventValidator = new EventValidator(eventRepository);

        final AdminView  adminView = new AdminView();
        final EmployeeView employeeView = new EmployeeView();
        view = new LoginView();
        User loggedUser = new User();

        new LoginController(loggedUser,view,adminView,employeeView,authenticationService,userValidator);
        new EmployeeController(loggedUser,employeeView,eventService,accountService,clientService,accountValidator,clientValidator);
        new AdminController(adminView,eventService,employeeService,userValidator,eventValidator);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Bank");
        stage.setScene(view.getMainScene());
        stage.centerOnScreen();
        view.setPrimaryStage(stage);
        stage.show();
    }
}
