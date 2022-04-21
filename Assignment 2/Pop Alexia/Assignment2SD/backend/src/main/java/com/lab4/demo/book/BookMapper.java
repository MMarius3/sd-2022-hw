package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "title", source = "book.title"),
            @Mapping(target = "author", source = "book.author"),
            @Mapping(target = "genre", source = "book.genre"),
            @Mapping(target = "quantity", source = "book.quantity"),
            @Mapping(target = "price", source = "book.price")
    })
    BookDTO toDto(Book book);

    @Mappings({
            @Mapping(target = "title", source = "bookDTO.title"),
            @Mapping(target = "author", source = "bookDTO.author"),
            @Mapping(target = "genre", source = "bookDTO.genre"),
            @Mapping(target = "quantity", source = "bookDTO.quantity"),
            @Mapping(target = "price", source = "bookDTO.price")
    })
    Book fromDto(BookDTO bookDTO);

}
