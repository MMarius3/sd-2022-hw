package com.lab4.demo.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;

    @NotNull
    @Pattern(regexp="^[A-Za-z .]*$",message = "Book name must be only letters")
    private String title;

    @NotNull
    @Pattern(regexp="^[A-Za-z .]*$",message = "Author name must be only letters")
    private String author;

    @NotNull
    @Pattern(regexp="^[A-Za-z]*$",message = "Genre name must be only letters")
    private String genre;

    @NotNull(message = "Quantity must be a number and is required")
    private Integer quantity;

    @NotNull(message = "Price must be a number and is required")
    private Double price;

    public String toString(){
        return id + "," + title + "," + author + "," + genre + "," + quantity + "," + price;
    }
}
