package com.assignment2.book_store.data.dto.book.mutations;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BookCreateRequestDto {

    @NotBlank
    @Size(max = 128)
    private String title;
    @NotBlank
    @Size(max = 64)
    private String authorFirstName;
    @NotBlank
    @Size(max = 32)
    private String authorLastName;
    @NotBlank
    private String publishingDate;
    @NotNull
    private Integer genreId;
    @NotNull
    private Long ownerId;
    @NotNull
    private Long imageId;

}
