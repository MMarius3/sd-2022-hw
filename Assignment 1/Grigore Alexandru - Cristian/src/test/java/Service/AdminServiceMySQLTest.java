package Service;

import Database.DatabaseConnectionFactory;
import Model.Builder.UserBuilder;
import Model.User;
import Repository.Security.RightRolesRepositoryMySQL;
import Repository.Security.RightsRolesRepository;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import Service.User.AdminService;
import Service.User.AdminServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class AdminServiceMySQLTest {

    private static AdminService adminService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeAll
    public static void setUp(){
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        adminService = new AdminServiceMySQL(userRepository);
    }

    @BeforeEach
    public void cleanUp(){
        userRepository.removeAll();
    }

    @Test
    public void addEmployee(){
        User user = new UserBuilder()
                .setUsername("test")
                .setPassword("test")
                .setRole(rightsRolesRepository.findRoleById(2L))
                .build();
        adminService.addEmployee(user);
    }

    @Test
    public void editEmployee(){
        User user = new UserBuilder()
                .setUsername("test")
                .setPassword("test")
                .setRole(rightsRolesRepository.findRoleById(2L))
                .build();
        adminService.addEmployee(user);

        User editUser = new UserBuilder()
                .setUsername("test name")
                .setPassword("test")
                .setRole(rightsRolesRepository.findRoleById(2L))
                .build();
        adminService.editEmployee(editUser);
    }

    @Test
    public void deleteEmployee(){
        User user = new UserBuilder()
                .setUsername("test")
                .setPassword("test")
                .setRole(rightsRolesRepository.findRoleById(2L))
                .build();
        adminService.addEmployee(user);
        adminService.deleteEmployee(user);
    }

}
