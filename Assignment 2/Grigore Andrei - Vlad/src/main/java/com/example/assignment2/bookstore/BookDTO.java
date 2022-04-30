package com.example.assignment2.bookstore;

import com.example.assignment2.bookstore.model.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private int quantity;

    public static BookDTO toDTO(Book book){
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .quantity(book.getQuantity())
                .build();
    }
}
