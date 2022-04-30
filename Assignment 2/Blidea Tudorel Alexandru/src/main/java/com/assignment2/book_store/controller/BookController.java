package com.assignment2.book_store.controller;

import com.assignment2.book_store.data.dto.book.mutations.BookCreateRequestDto;
import com.assignment2.book_store.data.dto.book.mutations.BookPatchRequestDto;
import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.book.searchFilters.BookSearchFilter;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.assignment2.book_store.UrlMapping.BOOK;

@RestController
@RequestMapping(BOOK)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<BookResponseDto> getBooks(@ModelAttribute BookSearchFilter searchFilter, @PageableDefault Pageable pageable) {
        return bookService.getBooks(searchFilter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookResponseDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookResponseDto createBook(@RequestBody @Validated BookCreateRequestDto createRequest) {
        return bookService.createBook(createRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookResponseDto updateBook(@PathVariable Long id, @RequestBody BookPatchRequestDto patchRequest) {
        if (!Objects.equals(id, patchRequest.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, String.format("Id from pathVariable(%d) and requestBody(%d) does not match", id, patchRequest.getId()));
        }
        return bookService.updateBook(patchRequest);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookResponseDto patchBook(@PathVariable Long id, @RequestBody BookPatchRequestDto patchRequest) {
        if (!Objects.equals(id, patchRequest.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, String.format("Id from pathVariable(%d) and requestBody(%d) does not match", id, patchRequest.getId()));
        }
        return bookService.updateBook(patchRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

}
