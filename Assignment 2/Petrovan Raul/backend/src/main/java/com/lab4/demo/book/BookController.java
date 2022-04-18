package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.UrlMapping.ITEMS;
import static com.lab4.demo.UrlMapping.ITEMS_ID_PART;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO item) {
        return bookService.create(item);
    }

    @PutMapping(ITEMS_ID_PART)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO item) {
        return bookService.edit(id, item);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }
}
