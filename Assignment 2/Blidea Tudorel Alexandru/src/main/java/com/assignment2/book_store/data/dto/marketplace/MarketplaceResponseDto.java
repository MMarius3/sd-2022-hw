package com.assignment2.book_store.data.dto.marketplace;

import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.quality.QualityResponseDto;
import lombok.Data;

@Data
public class MarketplaceResponseDto {

    private Long id;
    private BookResponseDto book;
    private QualityResponseDto quality;
    private Float price;

}
