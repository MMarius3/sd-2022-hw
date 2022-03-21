import bussiness_layer.service.admin.AdminService;
import bussiness_layer.service.admin.AdminServiceMySQL;
import bussiness_layer.service.authentication.AuthenticationService;
import bussiness_layer.service.authentication.AuthenticationServiceMySQL;
import bussiness_layer.service.employee.EmployeeService;
import bussiness_layer.service.employee.EmployeeServiceMySQL;
import presentation_layer.controllers.AdminController;
import presentation_layer.controllers.EmployeeController;
import presentation_layer.controllers.LoginController;
import presentation_layer.view.AdminView;
import presentation_layer.view.EmployeeView;
import presentation_layer.view.LoginView;
import repository_layer.database_builder.JDBConnectionWrapper;
import repository_layer.repository.account.AccountRepository;
import repository_layer.repository.account.AccountRepositoryMySQL;
import repository_layer.repository.action.ActionRepository;
import repository_layer.repository.action.ActionRepositoryMySQL;
import repository_layer.repository.user.UserRepository;
import repository_layer.repository.user.UserRepositoryMySQL;
import repository_layer.repository.user_role.UserRoleRepository;
import repository_layer.repository.user_role.UserRoleRepositoryMySQL;

import java.sql.Connection;

import static repository_layer.database_builder.Constants.Schemas.PRODUCTION;

public class Main {
  public static void main(String[] args) {

    final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

    final UserRepository userRepository = new UserRepositoryMySQL(connection);
    final UserRoleRepository userRoleRepository = new UserRoleRepositoryMySQL(connection);
    final ActionRepository actionRepository = new ActionRepositoryMySQL(connection,userRoleRepository);
    final AccountRepository accountRepository = new AccountRepositoryMySQL(connection);

    final EmployeeService employeeService = new EmployeeServiceMySQL(userRepository, userRoleRepository, accountRepository, actionRepository);
    final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, userRoleRepository,employeeService);
    final AdminService adminService = new AdminServiceMySQL(userRepository, userRoleRepository, actionRepository);

    final LoginView loginView = new LoginView();
    final EmployeeView employeeView = new EmployeeView();
    final AdminView adminView = new AdminView();

    new EmployeeController(employeeView,employeeService);
    new AdminController(adminView,adminService);
    new LoginController(loginView, authenticationService, employeeView, adminView);
  }
}
