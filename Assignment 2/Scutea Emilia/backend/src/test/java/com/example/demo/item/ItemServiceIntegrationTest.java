package com.example.demo.item;

import com.example.demo.TestCreationFactory;
import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.crud.ItemService;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        itemRepository.saveAll(items);

        List<ItemDTO> all = itemService.findAll();

        Assertions.assertEquals(items.size(), all.size());
    }
}
