package com.example.bookstore.book.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    public void testBookCreation(){
        final Book book = new Book();

        final String title = "test";
        book.setTitle(title);

        assertEquals(title, book.getTitle());

        final String title2 = "test2";
        final String author = "io";
        final Book book2 = Book.builder().title(title2).author(author).build();

        assertEquals(title2, book2.getTitle());
        assertEquals(author, book2.getAuthor());

    }
}