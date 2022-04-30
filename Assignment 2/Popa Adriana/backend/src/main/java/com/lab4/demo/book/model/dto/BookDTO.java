package com.lab4.demo.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;

    @NotNull(message = "Title can't be null!")
    @Size(min = 3, max =30, message = "The length should be between 3 and 30!")
    private String title;

    @NotNull(message = "Author can't be null!")
    @Size(min = 3, max =30, message = "The length should be between 3 and 30!")
    private String author;


    @NotNull(message = "Genre can't be null!")
    @Size(min = 3, max =30, message = "The length should be between 3 and 30!")
    private String genre;

    @NotNull(message = "Quantity can't be null!")
    @Min(value = 0, message = "Min value is 0!")
    private Integer quantity;

    @NotNull(message = "Price can't be null!")
    @DecimalMin(value = "0.0", message = "Min price is 0.0")
    @Digits(integer = 3, fraction = 2, message = "The format is xxx.xx")
    private Double price;
}
