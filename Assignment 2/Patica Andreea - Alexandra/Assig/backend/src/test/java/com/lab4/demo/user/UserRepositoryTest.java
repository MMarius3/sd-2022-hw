package com.lab4.demo.user;

import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static com.lab4.demo.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }


    @Test
    public void addUser() {
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User userSaved = repository.save(User.builder()
                .username(randomString())
                .email(email)
                .password(password)
                .build());

        assertNotNull(userSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(User.builder()
                    .username(randomString())
                    .email(email)
                    .password(password)
                    .build());
        });
    }


    @Test
    public void findByUsername(){           //TODO
        String username = randomString();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = repository.save(User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build());

        repository.save(user);
        repository.findByUsername(username);
        assertEquals(user.getUsername(), username);
    }

    @Test
    public void existsByUsername(){
        String username = randomString();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = repository.save(User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build());

        repository.save(user);
        Boolean result = repository.existsByUsername(username);
        assertEquals(result, true);
    }

    @Test
    public void existsByEmail(){
        String username = randomString();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = repository.save(User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build());

        repository.save(user);
        Boolean result = repository.existsByEmail(email);
        assertEquals(result, true);
    }

    @Test
    public void findAllByRolesIsContaining(){
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        Set<Role> roles = new HashSet<>();
        Role role = new Role(1, ERole.EMPLOYEE);
        roles.add(role);
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .roles(roles)
                    .build();
            users.add(user);
            repository.save(user);
        }

        List<User> foundUsers = repository.findAllByRoleEquals(ERole.EMPLOYEE.toString());
        for(User user:foundUsers){
            assertTrue(user.getRoles().contains(role));
        }
    }


}
