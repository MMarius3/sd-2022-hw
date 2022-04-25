package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@Validated
@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor

public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @GetMapping(FIND_SEARCH_BAR)
    public List<BookDTO> findAllByFilter(@PathVariable String filter) {
        return bookService.findAllByFilter(filter);
    }

    @PutMapping( SELL+BOOKS_ID_PART)
    public void sellBook(@Valid @RequestBody BookDTO bookDTO, @PathVariable Long id) {
        bookService.sellBook(id,bookDTO);
    }

    @DeleteMapping(BOOKS_ID_PART)
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Book deleted"));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BookDTO item) {
        bookService.create(item);
        return ResponseEntity.ok(new MessageResponse("Book created"));
    }

    @PutMapping(BOOKS_ID_PART)
    public ResponseEntity<?> edit(@PathVariable Long id, @Valid @RequestBody BookDTO item) {
         bookService.edit(id, item);
         return ResponseEntity.ok(new MessageResponse("Book edited"));
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }
}
