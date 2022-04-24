package com.example.bookstore.user;

import com.example.bookstore.book.BookRepository;
import com.example.bookstore.book.BookService;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.model.User;
import com.example.bookstore.user.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void create(){
        assertEquals(0, userService.allUsersForList().size());

        final User user1 = User.builder().email("ceva@gmail.com").username("user1").password("password").build();

        userRepository.save(user1);

        assertEquals(1, userService.allUsersForList().size());
    }

}