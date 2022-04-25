package com.lab4.demo.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BookDTO {
    private Long id;
    @NotNull(message = "Book title is required")
    @Size(
            min = 2,
            max = 100,
            message = "Book title '${title}' must be between {min} and {max} characters long")
    private String title;
    @NotNull(message = "Book author is required")
    @Size(
            min = 2,
            max = 100,
            message = "Book author '${author}' must be between {min} and {max} characters long")
    private String author;
    @NotNull(message = "Book genre is required")
    @Size(
            min = 2,
            max = 100,
            message = "Book genre '${genre}' must be between {min} and {max} characters long")
    private String genre;
    @NotNull(message = "Book quantity is required")
    @Min(value = 0,message = "Book quantity must be greater than 0")
    private Integer quantity;
    @NotNull(message = "Book price is required")
    @Digits(integer = 2 /*precision*/, fraction = 2 /*scale*/)
    private Double price;
}
