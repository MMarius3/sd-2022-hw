package com.assignment2.book_store.data.map;

import com.assignment2.book_store.data.dto.book.mutations.BookCreateRequestDto;
import com.assignment2.book_store.data.dto.book.mutations.BookPatchRequestDto;
import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.book.searchFilters.BookSearchFilter;
import com.assignment2.book_store.data.entity.jpa.Book;
import com.assignment2.book_store.exception.ErrorFactory;
import org.mapstruct.*;
import org.springframework.http.HttpStatus;

import java.time.format.DateTimeParseException;

import static com.assignment2.book_store.helper.ServiceHelper.*;
import static java.util.Optional.ofNullable;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "genre", source = "genre.genre"),
            @Mapping(target = "ownerUsername", source = "owner.username"),
            @Mapping(target = "ownerEmail", source = "owner.email"),
    })
    BookResponseDto entityToDto(Book book);

    @AfterMapping
    default void entityToModelMoreAttributes(Book book, @MappingTarget BookResponseDto response) {
        response.setPublishingDate(dateToString(book.getPublishingDate()));
        response.setImageName(String.format("%s.%s", book.getImage().getName(), book.getImage().getImageExtension().getExtension()));
    }

    @Mappings({
            @Mapping(target = "genre.id", source = "genreId"),
            @Mapping(target = "owner.id", source = "ownerId"),
            @Mapping(target = "image.id", source = "imageId"),
            @Mapping(target = "publishingDate", ignore = true)
    })
    Book createBook(BookCreateRequestDto bookCreate);

    @AfterMapping
    default void createBookMoreAttributes(BookCreateRequestDto createRequest, @MappingTarget Book book) {
        try {
            book.setPublishingDate(stringToDate(createRequest.getPublishingDate()));
        } catch (DateTimeParseException exception) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, String.format("The format for date is %s", getDateDefaultPattern()));
        }
    }

    @Mappings({
            @Mapping(target = "genre.genre", source = "genre")
    })
    Book createSearchFilter(BookSearchFilter searchFilter);

    default Book patchBook(Book original, BookPatchRequestDto patchRequest) {
        ofNullable(patchRequest.getTitle()).ifPresent(original::setTitle);
        ofNullable(patchRequest.getAuthorFirstName()).ifPresent(original::setAuthorFirstName);
        ofNullable(patchRequest.getAuthorLastName()).ifPresent(original::setAuthorLastName);
        ofNullable(patchRequest.getPublishingDate()).ifPresent(original::setTitle);
        ofNullable(patchRequest.getGenreId()).ifPresent(original.getGenre()::setId);
        ofNullable(patchRequest.getOwnerId()).ifPresent(original.getOwner()::setId);
        ofNullable(patchRequest.getImageId()).ifPresent(original.getImage()::setId);
        return original;
    }

}
