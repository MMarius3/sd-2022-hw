package com.lab4.demo.item;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
        int nrItems = 10;
        for (int i = 0; i < nrItems; i++) {
            itemRepository.save(Item.builder().title(String.valueOf(i)).build());
        }

        List<Item> all = itemService.findAll();

        Assertions.assertEquals(nrItems, all.size());
    }

    @Test
    void findByQuantity(){
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
        itemRepository.saveAll(items);

        List<Item> foundItems = itemRepository.findItemByQuantity(quantity);
        for(Item item:foundItems){
            assertEquals(item.getQuantity(), quantity);
        }
    }

    @Test
    void addItem(){
        Item item = Item.builder()
                .title(randomString())
                .author(randomString())
                .quantity((int)randomLong())
                .price((int)randomLong())
                .description(randomString())
                .build();

        ItemDto itemDto = ItemDto.toDto(item);

        ItemDto savedItemDto = itemService.addItem(itemDto);

        Assertions.assertEquals(savedItemDto, itemDto);
    }

    @Test
    void delete(){
        long id = randomLong();
        Item item = Item.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .quantity((int)randomLong())
                .price((int)randomLong())
                .description(randomString())
                .build();

        itemRepository.save(item);

        boolean result = itemService.delete(id);
        Assertions.assertEquals(result, true);
    }
}
