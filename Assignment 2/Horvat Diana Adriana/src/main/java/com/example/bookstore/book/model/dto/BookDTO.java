package com.example.bookstore.book.model.dto;

import com.example.bookstore.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private int quantity;
    private int price;

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .quantity(book.getQuantity())
                .price(book.getPrice())
                .build();
    }
}
