package com.lab4.demo.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab4.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.UrlMapping.BOOKS;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookControllerTest {
    protected MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        int nrOfBooks = 5;
        for (int i = 0; i < nrOfBooks; i++) {
            books.add(Book.builder().title("Book " + i).build());
        }

        //when(bookService.findAll()).thenReturn(books);
        doReturn(books).when(bookService).findAll();

        ResultActions resultActions = mockMvc.perform(get(BOOKS));

        String expectedJsonContent = new ObjectMapper().writeValueAsString(books);
        resultActions.andExpect(status().isOk());
                //.andExpect(content().json(expectedJsonContent, true));
    }
}
