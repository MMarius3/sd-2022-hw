package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookFilterRequestDto;
import com.lab4.demo.report.ReportServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @GetMapping(FILTERED)
    public Page<BookDTO> filteredBooks(@PathVariable String title, @PathVariable String author, @PathVariable String genre, @PathVariable Integer quantity,
                                       @PageableDefault(sort = {"title"}) Pageable pageable) {
        BookFilterRequestDto filter = new BookFilterRequestDto(title, author, genre, quantity);
        return bookService.findAllFiltered(filter, pageable);
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book) {
        return bookService.edit(id, book);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PutMapping(ENTITY)
    public void sell(@PathVariable Long id) {
        bookService.sell(id);
    }

    @GetMapping(ENTITY)
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.get(id);
    }

    @PostMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable Long id) {
        if (id == 0) {
            return reportServiceFactory.getReportService(CSV).export();
        } else {
            return reportServiceFactory.getReportService(PDF).export();
        }
    }
}
