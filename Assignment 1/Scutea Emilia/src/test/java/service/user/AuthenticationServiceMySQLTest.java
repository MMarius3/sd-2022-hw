package service.user;

import database.DBConnectionFactory;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationServiceMySQLTest {
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(false).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    }

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void register() {
        String username = "administrator@yahoo.ro";
        String password = "administrator2!";
        assertTrue(authenticationService.createAdministrator("administrator@yahoo.ro", "administrator2!"));
    }

    @Test
    public void login() {
        String username = "admin";
        String password = "administrator2!";

        authenticationService.register(username, password);

        User user = authenticationService.login(username, password);

        assertNotNull(user);
    }

    @Test
    public void logout() {

    }
}
