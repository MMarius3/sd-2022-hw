import database.DatabaseConnectionFactory;
import model.User;
import model.validation.Notification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationServiceMySQLTest {
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        authenticationService = new AuthenticationServiceMySQL(
                userRepository,
                rightsRolesRepository
        );
    }

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
    }


    @Test
    public void register() throws Exception {
        authenticationService.register("john@doe.com","qwerty123@");
        authenticationService.register("george@doe.com","qwerty123@");
        authenticationService.registerAsAdmin("simon@doe.com","qwerty123@");


        assertTrue(userRepository.findAllEmployees().size() == 2);
    }

    @Test
    public void login() throws Exception {
        authenticationService.register("john@doe.com","qwerty123@");
        Notification<User> loginNotification = authenticationService.login("john@doe.com", "qwerty123@");

        assertTrue(!loginNotification.hasErrors());
    }

    @Test
    public void login2() throws Exception {
        authenticationService.register("john@doe.com","qwerty123@");
        assertTrue(authenticationService.login("john@doe.com", "qwerty123@").
                getResult().getUsername().equals("john@doe.com"));
    }

    @Test
    public void registerAsAdmin() throws Exception {
        authenticationService.registerAsAdmin("george@doe.com","qwerty123@");
        authenticationService.register("john@doe.com","qwerty123@");

        assertTrue(userRepository.findAll().size() == 2);
    }

}
