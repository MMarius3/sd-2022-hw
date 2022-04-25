package com.lab4.demo.user;

import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
    }

    @Test
    void create(){
        User user = userRepository.save(User.builder()
                .username("Adriana")
                .password("1234dnffk")
                .email("popa@yahoo.com")
                .build());

        assertNotNull(user);
    }

    @Test
    void update(){
        User user = userRepository.save(User.builder()
                .username("Adriana")
                .password("1234dnffk")
                .email("popa@yahoo.com")
                .build());

        assertNotNull(user);

        user.setUsername("Popa");
        User updated = userRepository.save(user);

        assertEquals("Popa", updated.getUsername());
    }

    @Test
    void delete(){
        User user = userRepository.save(User.builder()
                .username("Adriana")
                .password("1234dnffk")
                .email("popa@yahoo.com")
                .build());

        assertNotNull(user);

        userRepository.delete(user);

        assertEquals(0, userRepository.findAll().size());
    }

}