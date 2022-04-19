package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookRequestDTO;
import com.lab4.demo.book.model.dto.BookResponseDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.BOOKS;
import static com.lab4.demo.UrlMapping.BOOKS_ID_PART;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
@AllArgsConstructor
public class BookController {
    private BookService bookService;

    @GetMapping
    public List<BookDTO> findAll() {
        return bookService.findAll();
    }

    @PostMapping
    public BookResponseDTO create(@RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.create(bookRequestDTO);
    }

    @DeleteMapping(BOOKS_ID_PART)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PutMapping(BOOKS_ID_PART)
    public BookResponseDTO update(@PathVariable Long id, @RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.update(id, bookRequestDTO);
    }

    @PatchMapping(BOOKS_ID_PART)
    public BookResponseDTO rename(@PathVariable Long id, @RequestBody String newTitle) {
        return bookService.rename(id, newTitle);
    }
}
