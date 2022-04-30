package com.example.assignment2.bookstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void testBookCreation(){
        final Book book = new Book();

        final String title = "title";
        book.setTitle(title);
        assertEquals(title,book.getTitle());

        String title2 = "title2";
        final Book book2 = Book.builder().title(title2).build();
        assertEquals(title2, book2.getTitle());

    }

}