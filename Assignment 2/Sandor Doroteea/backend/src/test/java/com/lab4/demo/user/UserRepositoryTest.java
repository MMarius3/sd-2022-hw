package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.user.model.ERole.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void createUserTest() {
        User userSaved = repository.save(User.builder()
                .username("usernamw")
                .password("kelly")
                .email("horror@yahoo.com")
                .build());
        assertNotNull(userSaved);
        assertEquals(1,repository.findAll().size());
        }

    @Test
    public void updateUserTest() {
        User userSaved = repository.save(User.builder()
                .username("usernamw")
                .password("kelly")
                .email("horror@yahoo.com")
                .build());
        userSaved.setUsername("newUsername");
        User userUpdated = repository.save(userSaved);
        assertNotEquals("usernamw",userUpdated.getUsername());
    }

    @Test
    public void deleteUserTest() {
        User userSaved = repository.save(User.builder()
                .username("usernamw")
                .password("kelly")
                .email("haj@yahoo.com")
                .build());
        repository.delete(userSaved);
        assertEquals(0,repository.findAll().size());
    }

}
