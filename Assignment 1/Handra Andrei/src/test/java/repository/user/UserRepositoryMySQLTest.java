package repository.user;

import database.DBConnectionFactory;
import model.Client;
import model.User;
import model.builder.UserBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryMySQLTest {

    private static UserRepository userRepository;
    private static Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();

    @BeforeAll
    public static void setupClass() {
        userRepository = new UserRepositoryMySQL(connection,new RightsRolesRepositoryMySQL(connection));

    }

    @BeforeEach
    void cleanUp() {
        userRepository.removeAll();
    }

    @AfterAll
    public static void clean(){
        userRepository.removeAll();
    }

    @Test
    void findAll() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(users.size(),0);
    }

    @Test
    void findAllWhenDBNotEmpty() throws Exception {

        User user1 = new UserBuilder()
                .setUsername("andrei")
                .setPassword("pass")
                .setRoles(new ArrayList<>())
                .build();
        User user2 = new UserBuilder()
                .setUsername("andreii")
                .setPassword("pass")
                .setRoles(new ArrayList<>())
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = userRepository.findAll();
        assertEquals(users.size(),2);
    }

    @Test
    void findByUsernameAndPassword() {
        User user1 = new UserBuilder()
                .setUsername("andrei")
                .setPassword("pass")
                .setRoles(new ArrayList<>())
                .build();
        User user2 = new UserBuilder()
                .setUsername("andreii")
                .setPassword("pass")
                .setRoles(new ArrayList<>())
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        User user3 = userRepository.findByUsernameAndPassword("andreii","pass");
        assertEquals(user3.getUsername(),user2.getUsername());

    }

    @Test
    void save() {
        assertTrue(userRepository.save(new UserBuilder()
        .setUsername("andrei")
        .setPassword("pass")
         .setRoles(new ArrayList<>())
        .build()));
    }

    @Test
    void removeAll() {
        User user1 = new UserBuilder()
                .setUsername("andrei")
                .setPassword("pass")
                .setRoles(new ArrayList<>())
                .build();
        User user2 = new UserBuilder()
                .setUsername("andreii")
                .setPassword("pass")
                .setRoles(new ArrayList<>())
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = userRepository.findAll();
        assertEquals(users.size(),2);
        userRepository.removeAll();
        users = userRepository.findAll();
        assertEquals(users.size(),0);
    }


}