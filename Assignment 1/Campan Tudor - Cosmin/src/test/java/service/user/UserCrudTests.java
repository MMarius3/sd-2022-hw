package service.user;

import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.*;

public class UserCrudTests {

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

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
    }



    @Test
    public void findByUsernameTST() throws Exception{
        String username = "TEST2";
        String password = "1234567";
        authenticationService.register(username, password);
        User user= userRepository.findByUsername(username);
        assertNotNull(user);

    }

    @Test
    public void deleteByUsernameTST() throws Exception{
        String username = "TEST3";
        String password = "1234567";
        authenticationService.register(username, password);
        userRepository.deleteByUsername(username);
        assertEquals(null,userRepository.findByUsername(username));
    }

//    @Test
//    public void updateByUsernameTST() throws Exception{
//        String username = "TEST4";
//        String password = "1234567";
//        authenticationService.register(username, password);
//
//        Role customerRole = new Role((long) 2,"employee");
//
//        User user = new UserBuilder()
//                .setUsername("ChangedUsername")
//                .setPassword("ChangedPassword")
//                .setRoles(Collections.singletonList(customerRole))
//                .build();
//        userRepository.updateByUsername(username);
//        assertEquals(null,userRepository.findByUsername(username));
//    }






}
