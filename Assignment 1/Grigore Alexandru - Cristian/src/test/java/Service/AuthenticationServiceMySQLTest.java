package Service;

import Database.DatabaseConnectionFactory;
import Model.User;
import Repository.Security.RightRolesRepositoryMySQL;
import Repository.Security.RightsRolesRepository;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import Service.User.AuthenticationService;
import Service.User.AuthenticationServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationServiceMySQLTest {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp(){
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        authenticationService = new AuthenticationServiceMySQL(userRepository,rightsRolesRepository);
    }

    @BeforeEach
    public void cleanUp(){
        userRepository.removeAll();
    }

    @Test
    public void register() throws Exception{
        assertTrue(authenticationService.register("Test username", "test password"));
    }

    @Test
    public void login() throws Exception{
        String username = "TEST";
        String password = "123456";
        authenticationService.register(username, password);
        User user = authenticationService.login(username, password);
        assertNotNull(user);
    }
}
