package com.book;

import com.book.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testCreateBook (){
        final Book book=new Book();

        String the_lord_of_the_rings = "The Lord of the Rings";
        book.setTitle(the_lord_of_the_rings);

        assertEquals(the_lord_of_the_rings, book.getTitle());

        String fantasy = "Fantasy";
        final Book book2=Book.builder()
                .author("J.R.R. Tolkien").genre(fantasy).title("Test123").build();

        assertEquals(fantasy, book2.getGenre());
    }

}