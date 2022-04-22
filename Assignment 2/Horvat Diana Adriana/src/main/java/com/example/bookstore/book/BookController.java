package com.example.bookstore.book;

import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bookstore.UrlMapping.ENTITY;
import static com.example.bookstore.UrlMapping.ITEMS;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @CrossOrigin
    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @CrossOrigin
    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping(ENTITY)
    public BookDTO changeName(@PathVariable Long id, @RequestBody Double newPrice) {
        return bookService.changePrice(id, newPrice);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book) {
        return bookService.edit(id, book);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping(ENTITY)
    public BookDTO getItem(@PathVariable Long id) {
        return bookService.get(id);
    }

}
