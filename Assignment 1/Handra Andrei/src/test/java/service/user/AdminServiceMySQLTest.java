package service.user;

import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.*;

class AdminServiceMySQLTest {

    private static AdminService adminService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static UserValidator userValidator;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        userValidator = new UserValidator(userRepository);

        adminService = new AdminServiceMySQL(
                userRepository,
                rightsRolesRepository
        );
    }

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void createEmployee(){

        String username = "testemployee@app.com";
        String password = "TestPassword2!";

        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        userValidator.validate(username,password);
        final List<String> errors = userValidator.getErrors();
        assertEquals(0,errors.size());
        assertTrue(adminService.createEmployee(username,password));
    }

    @Test
    public void createBadEmployee(){

        String username = "testEmployee";
        String password = "TestPassword";

        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        userValidator.validate(username,password);
        final List<String> errors = userValidator.getErrors();
        assertTrue(errors.size() > 0);
    }

}