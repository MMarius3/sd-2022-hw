package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book book);

    Book fromDto(BookDTO book);
}
