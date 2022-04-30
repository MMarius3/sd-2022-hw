package com.assignment2.book_store.service;

import com.assignment2.book_store.data.dto.book.mutations.BookCreateRequestDto;
import com.assignment2.book_store.data.dto.book.mutations.BookPatchRequestDto;
import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.book.searchFilters.BookSearchFilter;
import com.assignment2.book_store.data.entity.jpa.Book;
import com.assignment2.book_store.data.map.BookMapper;
import com.assignment2.book_store.repository.jpa.GenreRepository;
import com.assignment2.book_store.repository.jpa.ImageRepository;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.repository.jpa.BookRepository;
import com.assignment2.book_store.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final ExampleMatcher DEFAULT_EXAMPLE_MATCHER = ExampleMatcher.matchingAny()
            .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("genre.genre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final ImageRepository imageRepository;

    public Page<BookResponseDto> getBooks(BookSearchFilter searchFilter, Pageable pageable) {
        Book bookSearchFilter = bookMapper.createSearchFilter(searchFilter);

        Example<Book> bookExample = Example.of(bookSearchFilter, DEFAULT_EXAMPLE_MATCHER);

        return bookRepository.findAll(bookExample, pageable)
                .map(bookMapper::entityToDto);
    }

    public BookResponseDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::entityToDto)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "There is no book with given id!"));
    }

    public BookResponseDto createBook(BookCreateRequestDto createRequest) {
        Book createdBook = bookRepository.save(bookMapper.createBook(createRequest));
        return bookMapper.entityToDto(getCreated(createdBook));
    }

    public BookResponseDto updateBook(BookPatchRequestDto patchRequest) {
        Book originalBook = bookRepository.findById(patchRequest.getId())
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.NOT_FOUND, "There is no book with given id!"));

        return bookMapper.entityToDto(bookRepository.save(bookMapper.patchBook(originalBook, patchRequest)));
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    private Book getCreated(Book createdBook) {
        createdBook.setOwner(userRepository.getById(createdBook.getOwner().getId()));
        createdBook.setGenre(genreRepository.getById(createdBook.getGenre().getId()));
        createdBook.setImage(imageRepository.getById(createdBook.getImage().getId()));
        return createdBook;
    }

}
