package service.user;

import database.DBConnectionFactory;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.action.ActionRepository;
import repository.action.ActionRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AdminActionServiceMySQLTest {
    private static AdminActionService adminActionService;
    private static UserRepository userRepository;

    @BeforeAll

    public static void setUp(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        ActionRepository actionRepository = new ActionRepositoryMySQL(connection);
        adminActionService = new AdminActionServiceMySQL(userRepository,rightsRolesRepository,actionRepository);
    }

    @BeforeEach
    public void cleanUp(){
        userRepository.removeAll();
    }

    @Test
    void createEmployee() {
        assertTrue(
                adminActionService.createEmployee("Test Employee","Test password")
        );
    }

    @Test
    void createAction() throws Exception {
        adminActionService.createEmployee("Test Employee","Test password");
        User user =userRepository.findByUsernameAndPassword("Test Employee");
        assertTrue(
                adminActionService.createAction(user,"test","test description")
        );
    }

    @Test
    void updateEmployee() {
        adminActionService.createEmployee("Test Employee","Test password");
        User user =userRepository.findByUsernameAndPassword("Test Employee");
        assertTrue(
                adminActionService.updateEmployee("username",user,"Test New",null,null)
        );
    }

    @Test
    void deleteEmployee(){
        adminActionService.createEmployee("Test Employee","Test password");
        User user =userRepository.findByUsernameAndPassword("Test Employee");
        adminActionService.deleteEmployee(user);
        assertFalse(
                userRepository.existsByUsername("Test employee").hasErrors()
        );
    }
}