import controller.EmployeeController;
import controller.LoginController;
import database.JDBConnectionWrapper;
import model.validator.UserValidator;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {
  public static void main(String[] args) {
    final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

    final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
    final ClientRepositoryMySQL clientRepository = new ClientRepositoryMySQL(connection);
    final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
        rightsRolesRepository);
    final AccountRepositoryMySQL accountRepository= new AccountRepositoryMySQL(connection);
    final LoginView loginView = new LoginView();

    final EmployeeView employeeView = new EmployeeView();
    final EmployeeController employeeController=new EmployeeController(employeeView,clientRepository, accountRepository);

    employeeView.setVisible(false);

    final UserValidator userValidator = new UserValidator(userRepository);

    new LoginController(loginView, authenticationService, userValidator,employeeView);
  }
}
