package com.lab4.demo.item;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    /*
    public List<Item> findAllEnables(){
        return itemRepository.findAllByEnabledIsTrue()
                .collect(Collectors.toList());
    }

     */

//    @Transactional //?
//    public List<ItemDto> findAllEnabled(){
//        return itemRepository.findAllByEnabledIsTrue()
//                .map(ItemDto::toDto)
//                .collect(Collectors.toList());
//    }

    public ItemDto findById(Long id){
        return itemRepository.findById(id)
                .map(ItemDto::toDto)
                .orElseThrow(() -> new RuntimeException(format("Item with id %s not found", id)));
    }

    public boolean addItem(@NotNull ItemDto itemDto){
        Item item = null;
        item = itemRepository.saveAndFlush(Item.builder()
                .tile(itemDto.getTitle())
                .author(itemDto.getAuthor())
                .price(itemDto.getPrice())
                .quantity(itemDto.getQuantity())
                .description(itemDto.getDescription())
                .build());
        return item!=null;
    }
}
