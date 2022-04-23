package com.example.bookstore.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String name;
    private String author;
    private String description;
    private String genre;
    private Long quantity;
    private Float price;

    @Override
    public String toString() {
        return id + "," +
                name + "," +
                description + "," +
                author + "," +
                genre + "," +
                price + ",";
    }
}
