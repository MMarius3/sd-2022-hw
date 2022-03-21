package repository;

import database.DBConnectionFactory;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.authentication.AuthenticationService;
import service.authentication.AuthenticationServiceMySQL;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryMySQLTest {

    private static UserRepository userRepository;
    private static AuthenticationService authenticationService;

    @BeforeAll
    public static void setupClass() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
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
    public void findAll(){
        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {

        authenticationService.register("username", "password");
        authenticationService.register("username1", "password1");
        authenticationService.register("username2", "password2");


        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 3);
    }
}
