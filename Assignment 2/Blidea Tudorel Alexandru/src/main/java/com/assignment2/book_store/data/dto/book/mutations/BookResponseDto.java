package com.assignment2.book_store.data.dto.book.mutations;

import lombok.Data;

@Data
public class BookResponseDto {

    private Long id;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String publishingDate;
    private String genre;
    private String imageName;
    private String ownerUsername;
    private String ownerEmail;

    public static String getHeaders() {
        return "id, title, authorFirstName, authorLastName, publishingDate, genre, imageName, ownerUsername, ownerEmail";
    }

    @Override
    public String toString() {
        return id + ", " + title + ", " + authorFirstName + ", " + authorLastName + ", " + publishingDate + ", " + genre + ", " + imageName + ", "
                + ownerUsername + ", " + ownerEmail;
    }

}
