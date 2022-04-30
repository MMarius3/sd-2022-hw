package com.assignment2.book_store.data.dto.premarketplace;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PreMarketplaceCreateOfferDto {

    @NotNull
    private Long bookId;
    @NotNull
    private Float price;
    @NotNull
    private Integer qualityId;

}
