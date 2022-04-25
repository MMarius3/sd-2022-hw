package com.assignment2.book_store.data.dto.genre;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class GenrePatchRequestDto {

    private Integer id;
    @Size(max = 32)
    private String name;

}
