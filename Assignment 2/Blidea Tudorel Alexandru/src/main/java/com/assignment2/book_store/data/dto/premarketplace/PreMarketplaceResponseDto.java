package com.assignment2.book_store.data.dto.premarketplace;

import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.quality.QualityResponseDto;
import lombok.Data;

@Data
public class PreMarketplaceResponseDto {

    private Long id;
    private BookResponseDto book;
    private QualityResponseDto quality;
    private Float price;

}
