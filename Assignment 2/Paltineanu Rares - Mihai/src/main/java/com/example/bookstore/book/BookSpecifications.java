package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookFilterRequestDTO;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

public class BookSpecifications {
    public static Specification<Book> similarTitles(String title) {
        return (root, query, cb) -> cb.like(root.get("name"), title);
    }

    public static Specification<Book> similarGenres(String genre) {
        return (root, query, cb) -> cb.like(root.get("genre"), genre);
    }

    public static Specification<Book> similarAuthors(String author) {
        return (root, query, cb) -> cb.like(root.get("author"), author);
    }

    public static Specification<Book> specificationsFromFilter(BookFilterRequestDTO filter) {
        final Specification<Book> spec = (root, query, cb) -> cb.and();
        ofNullable(filter.getName()).ifPresent(name -> spec.and(similarTitles(name)));
        ofNullable(filter.getAuthor()).ifPresent(author -> spec.and(similarAuthors(author)));
        ofNullable(filter.getGenre()).ifPresent(genre -> spec.and(similarGenres(genre)));
        return spec;
    }
}
