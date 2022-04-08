package com.lab4.demo.book.model.dto;

import com.lab4.demo.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private String genre;

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .build();
    }
}
