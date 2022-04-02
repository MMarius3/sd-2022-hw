package service.user;

import database.DBConnectionFactory;
import model.Client;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

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

        authenticationService = new AuthenticationServiceMySQL(
                userRepository,
                rightsRolesRepository
        );
    }

    @Test
    public void register() throws Exception {
        assertTrue(
                authenticationService.register("Test Username", "Test Password")
        );
    }

    @Test
    public void login() throws Exception {
        String username = "TEST";
        String password = "123456";
        authenticationService.register(username, password);

        User user = authenticationService.login(username, password);

        assertNotNull(user);
    }

    @Test
    public void logout() throws Exception {

    }

    @Test
    public void findById() throws Exception {
        Long id = 1L;
        assertThrows(IllegalArgumentException.class, () -> authenticationService.findById(id));
    }

    @Test
    public void addEmployee() throws Exception {
        String name = "new.macean@gmail.com";
        assertTrue(authenticationService.addEmployee(name));
    }

    @Test
    public void updateEmployee() throws Exception {
        Long id = 1L;
        String name = "updated.macean@gmail.com";
        assertTrue(authenticationService.updateEmployee(id, name));
    }

    @Test
    public void viewEmployee() throws Exception {
        Long id = 8L;
        assertTrue(authenticationService.viewEmployee(id) != null);
    }

    @Test
    public void deleteEmployee() throws Exception {
        Long id = 8L;
        assertTrue(authenticationService.deleteEmployee(id));
    }

    @Test
    public void generateReport() throws Exception {
        Long id = 9L;
        assertTrue(authenticationService.generateReport(id));
    }
}