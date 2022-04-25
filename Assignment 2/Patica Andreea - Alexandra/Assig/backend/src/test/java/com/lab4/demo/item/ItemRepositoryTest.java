package com.lab4.demo.item;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
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
        Item itemSaved = repository.save(Item.builder().title("whatever").build());

        assertNotNull(itemSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Item.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        int nrItems = 10;
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < nrItems; i++) {
            items.add(Item.builder()
                            .title(String.valueOf(i))
                            .author(randomString())
                            .quantity((int)randomLong())
                            .price((int)randomLong())
                            .build());
        }
        repository.saveAll(items);
        List<Item> all = repository.findAll();
        assertEquals(items.size(), all.size());
    }

    @Test
    public void findItemByQuantity(){
        Integer quantity = (int) randomLong();
        int nrItems = 10;
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < nrItems; i++) {
            items.add(Item.builder()
                    .title(String.valueOf(i))
                    .author(randomString())
                    .quantity(quantity)
                    .price((int)randomLong())
                    .build());
        }

        repository.saveAll(items);
        List<Item> foundItems = repository.findItemByQuantity(quantity);
        for(Item item:foundItems){
            assertEquals(item.getQuantity(), quantity);
        }
    }
}
