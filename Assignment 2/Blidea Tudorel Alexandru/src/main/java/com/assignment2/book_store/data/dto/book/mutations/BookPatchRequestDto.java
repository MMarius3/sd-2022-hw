package com.assignment2.book_store.data.dto.book.mutations;

import lombok.Data;

@Data
public class BookPatchRequestDto {

    private Long id;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String publishingDate;
    private Integer genreId;
    private Long ownerId;
    private Long imageId;

}
