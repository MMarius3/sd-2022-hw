package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookFilterRequestDto;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Optional.ofNullable;

public class BookSpecifications {

    public static Specification<Book> similarTitles(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), title);
    }

    public static Specification<Book> similarAuthors(String author) {
        return (root, query, cb) -> cb.like(root.get("author"), author);
    }

    public static Specification<Book> similarGenres(String genre) {
        return (root, query, cb) -> cb.like(root.get("genre"), genre);
    }

    public static Specification<Book> largeStock(Integer stock) {
        return (root, query, cb) -> cb.greaterThan(root.get("quantity"), stock);
    }

    public static Specification<Book> specificationsFromFilter(BookFilterRequestDto filter) {
        final Specification<Book> spec = (root, query, cb) -> cb.and();
        ofNullable(filter.getTitle()).ifPresent(s -> spec.and(similarTitles(s)));
        ofNullable(filter.getAuthor()).ifPresent(s -> spec.and(similarAuthors(s)));
        ofNullable(filter.getGenre()).ifPresent(s -> spec.and(similarGenres(s)));
        ofNullable(filter.getOnlyLargeStock()).ifPresent(s -> spec.and(largeStock(s)));
        return spec;
    }
}