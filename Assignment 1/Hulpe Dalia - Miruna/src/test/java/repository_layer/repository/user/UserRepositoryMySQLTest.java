package repository_layer.repository.user;

import bussiness_layer.builder.UserBuilder;
import bussiness_layer.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository_layer.database_builder.DBConnectionFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryMySQLTest {
    private static UserRepository userRepository;

    @BeforeAll
    public static void setupClass() {

        userRepository = new UserRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection());
    }

    @Test
    void insert() {
        User user = new UserBuilder()
                .setUsername("TestUser")
                .setPassword("Pass123")
                .setId_series("CJ")
                .setId_number(123454l)
                .setPnc("5100313010095")
                .setAddress("home")
                .build();
        userRepository.insert(user);
    }

    @Test
    void findByUsernameAndPassword() {
        User user = userRepository.findByUsernameAndPassword("TestUser", "Pass123");
        assertEquals(java.util.Optional.ofNullable(user.getId()), java.util.Optional.ofNullable(4l));
    }

    @Test
    void findById() {
        User user = userRepository.findById(4l);
        assertEquals(java.util.Optional.ofNullable(user.getUsername()), java.util.Optional.ofNullable("TestUser"));
    }

    @Test
    void findByUsername() {
        User user = userRepository.findByUsername("TestUser");
        assertEquals(java.util.Optional.ofNullable(user.getId()), java.util.Optional.ofNullable(4l));
    }

    @Test
    void remove() {
        userRepository.remove("TestUser");
    }
}