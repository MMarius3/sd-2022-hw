package com.assignment2.book_store.data.map;

import com.assignment2.book_store.data.dto.genre.GenreCreateRequestDto;
import com.assignment2.book_store.data.dto.genre.GenrePatchRequestDto;
import com.assignment2.book_store.data.dto.genre.GenreResponseDto;
import com.assignment2.book_store.data.entity.jpa.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import static java.util.Optional.ofNullable;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mappings({
            @Mapping(source = "genre", target = "name")
    })
    GenreResponseDto entityToDto(Genre genre);

    @Mappings({
            @Mapping(source = "name", target = "genre")
    })
    Genre createGenre(GenreCreateRequestDto createRequest);

    default Genre patchGenre(GenrePatchRequestDto patchRequest, @MappingTarget Genre original) {
        ofNullable(patchRequest.getName()).ifPresent(original::setGenre);
        return original;
    }

}
