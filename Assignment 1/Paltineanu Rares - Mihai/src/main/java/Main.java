import controller.EmployeeController;
import controller.LoginController;
import database.Boostrap;
import database.JDBConnectionWrapper;
import model.Account;
import model.Client;
import model.validator.ClientAccountValidator;
import model.validator.ClientInformationValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.client.ClientService;
import service.client.account.AccountServiceMySQL;
import service.client.information.InformationServiceMySQL;
import service.user.authentication.AuthenticationService;
import service.user.authentication.AuthenticationServiceMySQL;
import view.EmployeeView;
import view.LoginView;
import view.client.information.UpdateInformationView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static database.Constants.Schemas.PRODUCTION;

public class Main {

    public static void main(String[] args) {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        final ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);

        try {
            new Boostrap(rightsRolesRepository);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rightsRolesRepository);
        final LoginView loginView = new LoginView();

        final UserValidator userValidator = new UserValidator(userRepository);

        final ClientInformationValidator clientInformationValidator = new ClientInformationValidator(clientRepository);
        final ClientAccountValidator clientAccountValidator = new ClientAccountValidator(clientRepository);

        ClientService<Client, Long> clientService = new InformationServiceMySQL(clientRepository);
        ClientService<Account, Long> accountService = new AccountServiceMySQL(accountRepository);
        final EmployeeController employeeController = new EmployeeController(new EmployeeView(),
                clientInformationValidator,
                clientAccountValidator,
                clientService,
                accountService);

        new LoginController(loginView, authenticationService, userValidator, employeeController);

//        new UpdateInformationView();
    }
}
