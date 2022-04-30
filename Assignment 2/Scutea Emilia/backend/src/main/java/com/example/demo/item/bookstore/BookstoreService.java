package com.example.demo.item.bookstore;

import com.example.demo.item.crud.ItemMapper;
import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    private Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public ItemDTO sellBook(Long id, ItemDTO item) {
        Item item1 = findById(id);
        ItemDTO itemDTO = itemMapper.toDto(item1);

        if (isEnoughQuantity(itemDTO, 1)) {
            Integer updateQuantity = itemDTO.getQuantity() - 1;

            itemRepository.sellBook(item.getId(), updateQuantity);
            item1 = findById(item.getId());
            itemDTO = itemMapper.toDto(item1);
        }
        return itemDTO;
    }

    private boolean isEnoughQuantity(ItemDTO itemDTO, Integer quantity) {
        if (itemDTO.getQuantity() > 0) {
            return itemDTO.getQuantity() - quantity >= 0;
        }
        return false;
    }

    public List<ItemDTO> searchItems(String str){
        String string = "%"+str+"%";
        return itemRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(string,string,string)
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }


}
