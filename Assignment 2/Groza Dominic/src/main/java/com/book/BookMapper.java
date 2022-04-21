package com.book;

import com.book.model.Book;
import com.book.model.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto (Book book);

    Book fromDto (BookDto bookDto);
}
