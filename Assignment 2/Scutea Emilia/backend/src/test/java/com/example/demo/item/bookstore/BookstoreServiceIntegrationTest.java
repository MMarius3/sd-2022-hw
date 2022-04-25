package com.example.demo.item.bookstore;

import com.example.demo.item.crud.ItemMapper;
import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookstoreServiceIntegrationTest {

    @Autowired
    private BookstoreService bookstoreService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void sellBook() {
        Item item = Item.builder()
                .title("Item")
                .author("Author")
                .quantity(20)
                .price(25.0)
                .genre("Comedy")
                .build();
        itemRepository.save(item);

        ItemDTO itemDTO = itemMapper.toDto(item);

        ItemDTO updatedItem = bookstoreService.sellBook(item.getId(), itemDTO);

        Assertions.assertEquals(item.getQuantity() - 1, updatedItem.getQuantity());
    }

    @Test
    void searchItems() {
        Item item = Item.builder()
                .title("Title")
                .author("Author")
                .genre("Genre")
                .quantity(10)
                .price(20.0)
                .build();
        itemRepository.save(item);
        List<ItemDTO> items = bookstoreService.searchItems("tle");
        Assertions.assertEquals(1, items.size());

    }


}