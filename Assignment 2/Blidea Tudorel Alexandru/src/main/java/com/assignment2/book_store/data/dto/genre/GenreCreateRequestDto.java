package com.assignment2.book_store.data.dto.genre;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class GenreCreateRequestDto {

    @NotBlank
    @Size(max = 32)
    private String name;

}
