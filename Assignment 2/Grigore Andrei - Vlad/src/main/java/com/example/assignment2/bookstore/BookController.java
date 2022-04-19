package com.example.assignment2.bookstore;

import com.example.assignment2.reports.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.assignment2.UrlMappings.BOOKS;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    public final BookService bookService;

//    @GetMapping
//    public List<BookDTO> findAll(){
//        return bookService.findAll();
//    }

    @GetMapping
    public String numaisuntem(){
        return "vulgari";
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PutMapping
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book) {
        return bookService.edit(id, book);
    }

}
