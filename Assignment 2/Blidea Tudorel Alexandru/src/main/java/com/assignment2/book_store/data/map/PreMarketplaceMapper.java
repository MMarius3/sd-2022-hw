package com.assignment2.book_store.data.map;


import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.premarketplace.PreMarketplaceCreateOfferDto;
import com.assignment2.book_store.data.dto.premarketplace.PreMarketplaceResponseDto;
import com.assignment2.book_store.data.dto.quality.QualityResponseDto;
import com.assignment2.book_store.data.entity.jpa.Book;
import com.assignment2.book_store.data.entity.jpa.Premarketplace;
import com.assignment2.book_store.data.entity.jpa.Quality;
import org.mapstruct.*;

import static com.assignment2.book_store.helper.ServiceHelper.dateToString;

@Mapper(componentModel = "spring")
public interface PreMarketplaceMapper {

    @Mappings({
            @Mapping(target = "quality", ignore = true),
            @Mapping(target = "book", ignore = true),
    })
    PreMarketplaceResponseDto entityToDto(Premarketplace premarketplace);

    QualityResponseDto entityToDto(Quality quality);

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

    @AfterMapping
    default void addQualityAndBook(Premarketplace entity, @MappingTarget PreMarketplaceResponseDto result) {
        result.setBook(entityToDto(entity.getBook()));
        result.setQuality(entityToDto(entity.getQuality()));
    }

    @Mappings({
            @Mapping(target = "book.id", source = "bookId"),
            @Mapping(target = "quality.id", source = "qualityId"),
    })
    Premarketplace createOffer(PreMarketplaceCreateOfferDto offerRequest);

}
