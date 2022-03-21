package service.user;

import database.JDBConnectionWrapper;
import model.Account;
import model.Action;
import model.User;
import model.builder.ActionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountServiceImplementation;

import java.sql.Date;

import static database.Constants.Schemas.TEST;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplementationTest {

    private UserService userService;
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        JDBConnectionWrapper connectionWrapper=new JDBConnectionWrapper(TEST);
        userService = new UserServiceImplementation(new UserRepositoryMySQL(connectionWrapper.getConnection(), new RightsRolesRepositoryMySQL(connectionWrapper.getConnection())));
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
        UserRepository userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);
        authenticationService = new AuthenticationServiceMySQL(
               userRepository, rightsRolesRepository, connectionWrapper.getConnection());
    }


    @Test
    void findAllEmployees() {
        assertEquals(0, userService.findAllEmployees().size());
    }

    @Test
    void findAllActions() {
        Long userId=1L;
        Date startDate=new Date(2022,03,01);
        Date endDate=new Date(2022,03,01);
        assertEquals(0, userService.findAllActions(userId,startDate,endDate).size());
    }

    @Test
    void remove() {
        String username = "TEST";
        String password = "123456";
        User user= authenticationService.register(username, password);
        assertTrue(userService.remove(user));
    }

    @Test
    void addAction() {
        String username = "TEST";
        String password = "123456";
        User user= authenticationService.register(username, password);
        assertTrue(userService.addAction(1L,"test"));
    }




}