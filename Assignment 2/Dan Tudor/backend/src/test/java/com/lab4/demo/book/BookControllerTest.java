package com.lab4.demo.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookRequestDTO;
import com.lab4.demo.book.model.dto.BookResponseDTO;
import com.lab4.demo.item.ItemController;
import com.lab4.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookControllerTest extends BaseControllerTest {
    //protected MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        /*MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();*/

        super.setUp();
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
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

        verify(bookService, times(1)).findAll();

        String expectedJsonContent = new ObjectMapper().writeValueAsString(books);
        resultActions.andExpect(status().isOk());
        //.andExpect(content().json(expectedJsonContent, true));
    }

    @Test
    public void create() throws Exception {
        /*final Long id = 1L;
        final String title = "Book 1";
        final String author = "Author 1";
        final String genre = "Genre 1";
        final int quantity = 1;
        final int price = 1;
        BookRequestDTO bookRequestDTO = BookRequestDTO.builder().title(title).author(author).genre(genre).quantity(quantity).price(price).build();
        BookResponseDTO bookResponseDTO = BookResponseDTO.builder().id(id).title(title).author(author).genre(genre).quantity(quantity).price(price).build();

        when(bookService.create(bookRequestDTO)).thenReturn(bookResponseDTO);

        final ResultActions resultActions = mockMvc.perform(
                post(BOOKS)
                        .content(contentToJson(bookRequestDTO)
                        .contentType(MediaType.APPLICATION_JSON))
                        .accept(MediaType.APPLICATION_JSON)
        );*/
        BookRequestDTO requestBook = BookRequestDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomInt(1, 10))
                .price(randomInt(1, 10))
                .build();
        BookResponseDTO responseBook = BookResponseDTO.builder()
                .id(1L)
                .title(requestBook.getTitle())
                .author(requestBook.getAuthor())
                .genre(requestBook.getGenre())
                .quantity(requestBook.getQuantity())
                .price(requestBook.getPrice())
                .build();

        when(bookService.create(requestBook)).thenReturn(responseBook);

        ResultActions result = performPostWithRequestBody(BOOKS, requestBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(responseBook));

    }

    @Test
    public void delete() throws Exception {
        long id = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKS + ENTITY, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }
    /*private String contentToJson(Object bookRequestDTO) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(bookRequestDTO);
    }*/
}
