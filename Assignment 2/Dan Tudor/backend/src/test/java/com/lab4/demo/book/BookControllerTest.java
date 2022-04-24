package com.lab4.demo.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookRequestDTO;
import com.lab4.demo.book.model.dto.BookResponseDTO;
import com.lab4.demo.item.ItemController;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
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
import static org.springframework.data.domain.Sort.Direction.ASC;
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
        super.setUp();
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    @Test
    public void searchList() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        String search = "%b%";
        when(bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike(search,search,search)).thenReturn(books);

//        ResultActions result = performGetWithPathVariable(SEARCH, search);
        ResultActions result = mockMvc.perform(get(BOOKS + SEARCH, search));

        verify(bookService, times(1)).findAllByTitleLikeOrAuthorLikeOrGenreLike(search,search,search);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    /*@Test
    public void findAllPaged() throws Exception {
        String search = "%b%";

        final int sortedPage = 4;
        final int sortedPageSize = 100;
        final String sortColumn = "title";
        final PageRequest pagination = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, sortColumn));

        Page<BookDTO> books = new PageImpl<>(listOf(BookDTO.class));
        when(bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike(search, search, search, pagination)).thenReturn(books);

        ResultActions result = performGetWithModelAttributeAndParams(BOOKS + SEARCH, Pair.of("search", search), pairsFromPagination(pagination));

        verify(bookService, times(1)).findAllByTitleLikeOrAuthorLikeOrGenreLike(search, search, search, pagination);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }*/

    @Test
    public void create() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomInt(1, 10))
                .price(randomInt(1, 10))
                .build();

        when(bookService.create(bookDTO)).thenReturn(bookDTO);

        ResultActions result = performPostWithRequestBody(BOOKS, bookDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));

    }

    @Test
    public void delete() throws Exception {
        long id = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKS + ENTITY, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomInt(1, 10))
                .price(randomInt(1, 10))
                .build();
        when(bookService.update(bookDTO.getId(), bookDTO)).thenReturn(bookDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + ENTITY, bookDTO, bookDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));
    }

    @Test
    public void sell() throws Exception {
        int quantity = 3;
        BookDTO bookDTO = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomInt(8, 10))
                .price(randomInt(5, 10))
                .build();
        when(bookService.sell(bookDTO.getId(), quantity)).thenReturn(bookDTO);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOKS + BOOKS_ID, quantity, bookDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));
    }

//    @Test
//    public void changeName() throws Exception {
//        String newTitle = "Title";
//        BookDTO bookDTO = BookDTO.builder()
//                .id(randomLong())
//                .title(randomString())
//                .author(randomString())
//                .genre(randomString())
//                .quantity(randomInt(1, 10))
//                .price(randomInt(1, 10))
//                .build();
//        when(bookService.rename(bookDTO.getId(), newTitle)).thenReturn(bookDTO);
//
//        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOKS + ENTITY, newTitle, bookDTO.getId());
//        result.andExpect(status().isOk())
//                .andExpect(jsonContentToBe(bookDTO));
//    }


}
