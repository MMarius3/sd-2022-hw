package com.lab4.demo.item;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lab4.demo.TestCreationFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemService = new ItemService(itemRepository);
    }

    @Test
    void findAll() {
        List<Item> items = new ArrayList<>();
        int nrItems = 10;
        for (int i = 0; i < nrItems; i++) {
            items.add(Item.builder().title(String.valueOf(i)).build());
        }

        when(itemRepository.findAll()).thenReturn(items);

        List<Item> all = itemService.findAll();

        Assertions.assertEquals(nrItems, all.size());
    }

//    @Test     //TODO
//    void findById(){
//        long id = randomLong();
//        Item item = Item.builder()
//                .id(id)
//                .title(randomString())
//                .author(randomString())
//                .quantity((int)randomLong())
//                .price((int)randomLong())
//                .build();
//
//        Optional<Item> optional =
//        ItemDto itemDto = ItemDto.toDto(item);
//
//        when(itemRepository.findById(id)).thenReturn(ItemDto.toDto(item));
//        ItemDto foundItem = itemService.findById(id);
//
//        Assertions.assertEquals(nrItems, all.size());
//    }

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

        when(itemRepository.findItemByQuantity(quantity)).thenReturn(items);

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

        when(itemRepository.save(item)).thenReturn(item);

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

        doNothing().when(itemRepository).deleteById(id);

        boolean result = itemService.delete(id);
        Assertions.assertEquals(result, true);
    }

}
