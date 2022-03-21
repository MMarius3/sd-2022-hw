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
import service.user.UserService;
import service.user.UserServiceImpl;

import java.sql.Connection;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceImplTest {
    private static UserService userService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        userService = new UserServiceImpl(userRepository,rightsRolesRepository);
    }

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
    }


    @Test
    public void createEmployee() throws Exception {
        userService.createEmployee("test@yahoo.com","qwerty123@");
        userService.createEmployee("q","30");

        assertTrue(userService.findAll().size() == 1);
    }

    @Test
    public void deleteUser() throws Exception{
        userService.createEmployee("test22@gmail.com","qwertyuiop12#");
        User user = userService.viewEmployee("test22@gmail.com").getResult();

        assertTrue(userService.deleteUser(user));
    }

    @Test
    public void updateUser() throws Exception{
        userService.createEmployee("test1@yahoo.com","qwerty123@");
        User user = userService.viewEmployee("test1@yahoo.com").getResult();

        userService.updateUser(user.getId(),"test1@gmail.com");

        assertTrue(Objects.equals(userService.findAllEmployees().get(0).getUsername(), "test1@gmail.com"));
    }

}
