package com.example.demo.item;

import com.example.demo.TestCreationFactory;
import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.GenreType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Item itemSaved = repository.save(Item.builder().title("whatever").genre("comedy").author("me").price(25.0).quantity(1).build());

        assertNotNull(itemSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Item.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        repository.saveAll(items);
        List<Item> all = repository.findAll();
        assertEquals(items.size(), all.size());
    }
}