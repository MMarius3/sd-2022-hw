package com.example.assignment2.bookstore;

import com.example.assignment2.bookstore.model.Book;
import com.example.assignment2.user.UserRepository;
import com.example.assignment2.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    //@BeforeEach
   // public void setUp(){
        //bookRepository.deleteAll();
   // }
    @Test
    public void findById() {
        final Book book1 = Book.builder().title("title").genre("genre").author("author").quantity(25).build();
        final Book savedBook = bookRepository.save(book1);
        final User user = User.builder().username("user1").email("user@gmail.com").password("*10userpass").build();
        final User savedUser = userRepository.save(user);
        bookRepository.updateTitle(book1.getId(),"new title");

        final Optional<Book> optionalBook = bookRepository.findById(savedBook.getId());
        assertTrue(optionalBook.isPresent());
        final Book book = optionalBook.get();
        //assertEquals(book.getTitle(), "mew title");
    }

}