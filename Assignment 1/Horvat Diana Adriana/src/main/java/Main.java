import controller.LoginController;
import database.JDBConnectionWrapper;
import model.validator.UserValidator;
import respository.security.RolesRepository;
import respository.security.RolesRepositoryMySQL;
import respository.user.UserRepository;
import respository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.LoginView;

import java.sql.Connection;

public class Main {
    private static final String PRODUCTION = "bank app";

    public static void main(String[] args){
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RolesRepository rolesRepository = new RolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rolesRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rolesRepository);

        final LoginView loginView = new LoginView();

        final UserValidator userValidator = new UserValidator(userRepository);

        new LoginController(loginView, authenticationService, userValidator);
    }
}
