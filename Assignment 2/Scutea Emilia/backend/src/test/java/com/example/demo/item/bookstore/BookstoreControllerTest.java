package com.example.demo.item.bookstore;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.item.crud.ItemController;
import com.example.demo.item.crud.ItemService;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.randomLong;
import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookstoreControllerTest extends BaseControllerTest {
    @InjectMocks
    private BookstoreController controller;

    @Mock
    private BookstoreService bookstoreService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookstoreController(bookstoreService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        List<ItemDTO> items = TestCreationFactory.listOf(Item.class);
        when(bookstoreService.findAll()).thenReturn(items);

        ResultActions response = performGet(BOOKSTORE);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void searchItems() throws Exception {
        List<ItemDTO> items = TestCreationFactory.listOf(Item.class);
        String str = "1";
        when(bookstoreService.searchItems(str)).thenReturn(items);

        ResultActions response = performGetWithPathVariables(BOOKSTORE + BOOKSTORE_SEARCH_ITEMS, str);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }
    @Test
    void sellBook() throws Exception {
        final long id = randomLong();
        ItemDTO reqItem = ItemDTO.builder()
                .title(randomString())
                .author(randomString())
                .quantity(20)
                .build();

        ItemDTO sellItem = reqItem;
        sellItem.setQuantity(sellItem.getQuantity()-1);

        when(bookstoreService.sellBook(id, reqItem)).thenReturn(sellItem);

        ResultActions result = performPutWithRequestBodyAndPathVariables(BOOKSTORE + BOOKSTORE_ID_SELL, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }


}