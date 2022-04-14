package service.user;

import database.DatabaseConnectionFactory;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceMySQLTest {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        rightsRolesRepository.addRole("Employee");
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
        assertTrue(
                authenticationService.register("Test Username", "1234567#")
        );
    }

    @Test
    public void login() throws Exception {
        String username = "Test";
        String password = "1234567#";
        authenticationService.register(username, password);

        User user = authenticationService.login(username, password);

        assertNotNull(user);
    }

    @Test
    public void logout() throws Exception {

    }

    @Test
    public void delete() throws Exception {
        String username = "Test";
        String password = "1234567#";
        authenticationService.register(username, password);
        assertTrue(authenticationService.delete(username));
    }

    @Test
    public void update() throws Exception {
        String username = "Test";
        String password = "1234567#";
        authenticationService.register(username, password);
        assertTrue(authenticationService.update(1L,username,password));
    }

    @Test
    public void findById() throws Exception{
        String username = "Test";
        String password = "1234567#";
        authenticationService.register(username, password);
        Optional<User> user1 = authenticationService.findById(2L);
        assertFalse(user1.isPresent());
    }

}