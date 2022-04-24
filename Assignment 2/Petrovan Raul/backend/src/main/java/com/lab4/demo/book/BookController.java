package com.lab4.demo.book;

import com.lab4.demo.book.model.Genre;
import com.lab4.demo.book.model.SearchObject;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.UrlMapping.BOOKS;
import static com.lab4.demo.UrlMapping.BOOKS_ID_PART;
import static com.lab4.demo.UrlMapping.FILTER;
import static com.lab4.demo.UrlMapping.GENRES;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @GetMapping(GENRES)
    public List<String> getAllGenres() {
        return Arrays.stream(Genre.values()).map(Genre::name).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/outOfStock")
    public List<BookDTO> outOfStock() {
        return bookService.findOutOfStock();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PutMapping(BOOKS_ID_PART)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book) {
        return bookService.edit(id, book);
    }

    @PutMapping(BOOKS_ID_PART + "/sellOne")
    public BookDTO sellOne(@PathVariable Long id) {
        return bookService.sellOne(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }

    @PostMapping(FILTER)
    public List<BookDTO> filter(@RequestBody SearchObject filterObject) {
        System.out.println(bookService.filter(filterObject.filter));
        return bookService.filter(filterObject.filter);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
