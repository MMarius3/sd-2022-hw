package com.example.demo.item.bookstore;

import com.example.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKSTORE)
@RequiredArgsConstructor
public class BookstoreController {

    private final BookstoreService bookstoreService;

    @GetMapping
    public List<ItemDTO> allItems() {
        return bookstoreService.findAll();
    }

    @GetMapping(BOOKSTORE_SEARCH_ITEMS)
    public List<ItemDTO> searchItems(@PathVariable String str) {
        return bookstoreService.searchItems(str);
    }

    @PutMapping(BOOKSTORE_ID_SELL)
    public ItemDTO sellBook(@PathVariable Long id, @RequestBody ItemDTO itemDTO){
        return bookstoreService.sellBook(id, itemDTO);
    }
}
