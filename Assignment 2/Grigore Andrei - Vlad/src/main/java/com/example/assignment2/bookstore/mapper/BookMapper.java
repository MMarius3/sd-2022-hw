package com.example.assignment2.bookstore.mapper;

import com.example.assignment2.bookstore.BookDTO;
import com.example.assignment2.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book book);
    Book fromDto(BookDTO book);
}
