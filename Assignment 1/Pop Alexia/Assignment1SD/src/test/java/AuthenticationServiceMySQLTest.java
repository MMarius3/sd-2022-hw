import Database.Constants;
import Database.DBConnectionFactory;
import Model.User;
import Repository.Security.RightsRolesRepository;
import Repository.Security.RightsRolesRepositoryMySQL;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import Service.Secutiry.AuthenticationService;
import Service.Secutiry.AuthenticationServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationServiceMySQLTest {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        connection.isValid(10);
    }

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
    }


    @Test
    public void register() throws Exception {
        assertTrue(
                authenticationService.register("Test Username", "test1*")
        );
    }

    @Test
    public void login() throws Exception {
        String username = "mariaapop@yahoo.com";
        String password = "alexia1*";
        authenticationService.register(username, password);

        User user = authenticationService.login(username, password);
        assertNotNull(user);
    }

    @Test
    public void logout() throws Exception {

    }

}
