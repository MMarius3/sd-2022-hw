package com.assignment2.book_store.data.map;

import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.marketplace.MarketplaceResponseDto;
import com.assignment2.book_store.data.dto.quality.QualityResponseDto;
import com.assignment2.book_store.data.entity.jpa.Book;
import com.assignment2.book_store.data.entity.jpa.Marketplace;
import com.assignment2.book_store.data.entity.jpa.Quality;
import org.mapstruct.*;

import static com.assignment2.book_store.helper.ServiceHelper.dateToString;

@Mapper(componentModel = "spring")
public interface MarketplaceMapper {

    @Mappings({
            @Mapping(target = "quality", ignore = true),
            @Mapping(target = "book", ignore = true),
    })
    MarketplaceResponseDto entityToDto(Marketplace marketplace);

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
    default void addQualityAndBook(Marketplace entity, @MappingTarget MarketplaceResponseDto result) {
        result.setBook(entityToDto(entity.getBook()));
        result.setQuality(entityToDto(entity.getQuality()));
    }

    default void patchOffer() {

    }

}
