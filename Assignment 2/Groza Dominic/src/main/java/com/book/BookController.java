package com.book;

import com.book.model.dto.BookDto;
import com.book.model.dto.BookFilterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @CrossOrigin
    @GetMapping
    public List<BookDto> allBooks() {
        return bookService.findAll();
    }

    @CrossOrigin
    @GetMapping(FILTERED)
    public Page<BookDto> filteredBooks(@ModelAttribute("filter") BookFilterRequestDto filter, @PageableDefault(sort
            = {"title"}) Pageable pageable){
        return bookService.findAllFiltered(filter,pageable);
    }

    @CrossOrigin
    @PostMapping
    public BookDto create(@RequestBody BookDto bookDto){
        return bookService.create(bookDto);
    }

    @CrossOrigin
    @PatchMapping(ENTITY)
    public BookDto changeQuantity(@PathVariable Long id, @RequestBody BookDto book){
        System.out.println("intra");
        return bookService.changeQuantity(id,book.getQuantity());
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public BookDto edit(@PathVariable Long id, @RequestBody BookDto book){
        return bookService.edit(id,book);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    @CrossOrigin
    @GetMapping(ENTITY)
    public BookDto getBook(@PathVariable Long id){
        return bookService.get(id);
    }


}
