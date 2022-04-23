package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.book.model.dto.BookFilterRequestDTO;
import com.example.bookstore.report.ReportServiceFactory;
import com.example.bookstore.report.ReportType;
import com.example.bookstore.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.bookstore.UrlMapping.*;


@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping(GET_BOOKS)
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @GetMapping(GET_BOOK)
    public BookDTO findBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping(ADD_BOOK)
    public ResponseEntity<?> create(@RequestBody BookDTO bookDTO) {
        bookService.create(bookDTO);
        return ResponseEntity.ok("Book created successfully");
    }

    @DeleteMapping(DELETE_BOOK)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok("Book with id " + id + " deleted successfully");
    }

    @PutMapping(UPDATE_BOOK)
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody BookDTO book) {
        bookService.edit(id, book);
        return ResponseEntity.ok("Book updated successfully");
    }

    @GetMapping(EXPORT_REPORT)
    public void exportReport(@PathVariable ReportType type) {
        List<String> books = this.allItems().stream()
                .filter(book -> book.getQuantity() == 0)
                .map(BookDTO::toString)
                .collect(Collectors.toList());
        reportServiceFactory.getReportService(type).export(books);
    }

    @GetMapping(FILTER_BOOKS)
    public Page<BookDTO> filter(@ModelAttribute("filter") BookFilterRequestDTO filter, @PageableDefault(sort
            = {"name"}) Pageable pageable) {
        return bookService.filterBooks(filter, pageable);
    }
}
