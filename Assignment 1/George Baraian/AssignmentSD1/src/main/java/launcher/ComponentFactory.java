package launcher;

import controller.LoginController;
import database.DBConnectionFactory;
import repositories.security.RightsRolesRepository;
import repositories.security.RightsRolesRepositoryMySQL;
import repositories.user.UserRepository;
import repositories.user.UserRepositoryMySQL;
import services.user.AuthenticationServiceMySQL;
import services.user.AuthenticationService;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {

    private final LoginView loginView;

    private final LoginController loginController;

    private final AuthenticationService authenticationService;
    //private final ClientService clientService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.loginView = new LoginView();
        this.loginController = new LoginController(loginView, authenticationService);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public LoginController getLoginController() {
        return loginController;
    }
}
