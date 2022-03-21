package service.user;

import database.DBConnectionFactory;
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
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {
    private static UserInfoService userInfoService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        userInfoService = new UserInfoServiceImpl(userRepository);
    }

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
        userInfoService.save(new UserBuilder().setUsername("a").setPassword("123").setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(ADMINISTRATOR))).build());
        userInfoService.save(new UserBuilder().setUsername("e1").setPassword("123").setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE))).build());
        userInfoService.save(new UserBuilder().setUsername("e2").setPassword("123").setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle(EMPLOYEE))).build());
    }

    @Test
    public void findAllEmployees(){
        List<User> employees = userInfoService.findAllWithRole(rightsRolesRepository.findRoleByTitle(EMPLOYEE));
        assertEquals(2, employees.size());
    }

    @Test
    public void updateEmployee(){
        List<User> employees = userInfoService.findAllWithRole(rightsRolesRepository.findRoleByTitle(EMPLOYEE));
        userInfoService.updateById(employees.get(0).getId(), new UserBuilder().setUsername("e3").setPassword(PasswordEncoder.encode("12")).build());
        employees = userInfoService.findAllWithRole(rightsRolesRepository.findRoleByTitle(EMPLOYEE));
        assertEquals(employees.get(0).getUsername(), "e3");
    }

    @Test
    public void deleteEmployee(){
        List<User> employees = userInfoService.findAllWithRole(rightsRolesRepository.findRoleByTitle(EMPLOYEE));
        int initSize = employees.size();
        userInfoService.removeById(employees.get(0).getId());
        employees = userInfoService.findAllWithRole(rightsRolesRepository.findRoleByTitle(EMPLOYEE));
        assertEquals(initSize-1, employees.size());
    }
}
