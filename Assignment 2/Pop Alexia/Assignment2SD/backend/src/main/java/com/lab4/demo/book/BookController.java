package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOK)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @GetMapping("/filter/{filter}")
    public List<BookDTO> filterBooks(@PathVariable String filter){return bookService.filterBooks(filter);}

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BookDTO book) {
        if(bookService.findByTitle(book.getTitle())!=null){
          throw new ConstraintViolationException("Book with this title already exists", Set.of());
        }
        bookService.create(book);
        return ResponseEntity.ok(new MessageResponse("Book created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id ,@Valid  @RequestBody BookDTO book) {
        if(!bookService.findById(id).getTitle().equals(book.getTitle())) {
           if(bookService.findByTitle(book.getTitle())!=null){
               throw new ConstraintViolationException("Book with this title already exists", Set.of());
           }
        }
        bookService.edit(id, book);
        return ResponseEntity.ok(new MessageResponse("Book edited successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Book deleted successfully"));
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse(e.getMessage()));
    }
}
