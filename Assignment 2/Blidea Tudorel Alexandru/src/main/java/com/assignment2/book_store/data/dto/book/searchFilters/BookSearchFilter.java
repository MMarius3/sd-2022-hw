package com.assignment2.book_store.data.dto.book.searchFilters;

import lombok.Data;

@Data
public class BookSearchFilter {

    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String genre;

}
