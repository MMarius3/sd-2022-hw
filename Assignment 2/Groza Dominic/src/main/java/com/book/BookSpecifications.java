package com.book;

import com.book.model.Book;
import com.book.model.dto.BookFilterRequestDto;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

    public static Specification<Book> similarTitles(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), title);
    }
    public static Specification<Book> similarGenres(String genre) {
        return (root, query, cb) -> cb.like(root.get("genre"), genre);
    }
    public static Specification<Book> similarAuthors(String author) {
        return (root, query, cb) -> cb.like(root.get("author"), author);
    }




    public static Specification<Book> specificationsFromFilter(BookFilterRequestDto filter) {
        final Specification<Book> spec=(root, query, cb)->cb.and();
        if(filter.getGenre()!=null) {
            spec.and(similarGenres(filter.getGenre()));
        }
        if(filter.getAuthor()!=null) {
            spec.and(similarAuthors(filter.getAuthor()));
        }
        if(filter.getTitle()!=null) {
            spec.and(similarTitles(filter.getTitle()));
        }
        return spec;
    }
}
