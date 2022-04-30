package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@Valid @RequestBody BookDTO bookDTO) {
        return bookService.create(bookDTO);
    }

    @PutMapping(BOOKS_ID_PART)
    public BookDTO edit(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO) {
        return bookService.edit(id, bookDTO);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return bookService.export(type);
    }

    @DeleteMapping(BOOKS_ID_PART)
    public void delete(@PathVariable long id){
        bookService.deleteByID(id);
    }

    @PutMapping(SELL)
    public BookDTO sell(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO){
        return bookService.sell(id,bookDTO);
    }

    @GetMapping(FIND)
    public List<BookDTO> findAllByTitleLikeOrAuthorLikeOrGenreLike(@PathVariable String find){
        return bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike(find,find,find);
    }
}
