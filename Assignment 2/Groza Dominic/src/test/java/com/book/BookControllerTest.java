package com.book;

import com.BaseControllerTest;
import com.TestCreationFactory;
import com.UrlMapping;
import com.book.model.Book;
import com.book.model.dto.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.UrlMapping.*;

import static com.TestCreationFactory.randomLong;
import static com.TestCreationFactory.randomString;
import static com.UrlMapping.ENTITY;
import static com.UrlMapping.ITEMS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import static com.sun.java.accessibility.util.EventID.ITEM;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookControllerTest extends BaseControllerTest { ;
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allBooks() throws Exception {
        List<BookDto> books= TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(ITEMS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    @Test
    void create() throws Exception {
        BookDto createdBook=BookDto.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();

        when(bookService.create(createdBook)).thenReturn(createdBook);
        ResultActions result=performPostWithRequestBody(ITEMS,createdBook);

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(createdBook));
    }

    @Test
    void changeQuantity() throws Exception {
        long quantity=randomLong();
        long id=randomLong();
        BookDto book=BookDto.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(quantity)
                .build();

        when(bookService.changeQuantity(id,quantity-1)).thenReturn(book);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(ITEMS + ENTITY,book, id);
        System.out.println(result.toString());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }

    @Test
    void edit() throws Exception {
        BookDto book=BookDto.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();

        when(bookService.edit(book.getId(), book)).thenReturn(book);

        ResultActions result = performPutWithRequestBodyAndPathVariable(ITEMS + ENTITY, book, book.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }

    @Test
    void delete() {
    }

    @Test
    void getBook() {
    }
}