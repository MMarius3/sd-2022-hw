package service.user;

import repository.user.UserRepository;
import service.user.authentication.AuthenticationService;
import database.DBConnectionFactory;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.authentication.AuthenticationServiceMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceMySQLTest {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    }

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void register() throws Exception {
        assertTrue(authenticationService.register("employee", "Password1!"));
    }

    @Test
    public void login() throws Exception {
        String username = "testUser";
        String password = "password1!!";
        authenticationService.register(username, password);

        User user = authenticationService.login(username, password);

        assertNotNull(user);

        User invalidUser = authenticationService.login(username + "a", password);
        assertNull(invalidUser);
    }
}
