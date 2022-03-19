import Database.DBConnectionFactory;
import Model.User;
import Repository.Security.RightsRolesRepository;
import Repository.Security.RightsRolesRepositoryMySQL;
import Repository.User.UserRepository;
import Repository.User.UserRepositoryMySQL;
import Service.Employee.EmployeeService;
import Service.Employee.EmployeeServiceMySQL;
import Service.Secutiry.AuthenticationService;
import Service.Secutiry.AuthenticationServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceMySQLTest {

    private static EmployeeService employeeService;
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        authenticationService = new AuthenticationServiceMySQL(userRepository,rightsRolesRepository);
        employeeService = new EmployeeServiceMySQL(userRepository);
        connection.isValid(10);
    }

    @BeforeEach
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void update() throws Exception {
        authenticationService.register("test@yahoo.com","Alexia1*");
        User user = userRepository.findByUsernameAndPassword("test@yahoo.com",encodePassword("Alexia1*"));
        employeeService.updateEmp( user.getId(),"test2@yahoo.com","");
        User user2 = userRepository.findById(user.getId());

        assertNotNull(user2);
        assertEquals("test2@yahoo.com",user2.getUsername());
    }

    @Test
    public void view() throws Exception {
        assertNotNull(employeeService.viewEmp());
    }

    @Test
    public void delete() throws  Exception{
        authenticationService.register("test2@yahoo.com","Alexia1*");
        employeeService.deleteEmp("test2@yahoo.com");
        assertTrue(authenticationService.register("test2@yahoo.com","alexia1*"));
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
