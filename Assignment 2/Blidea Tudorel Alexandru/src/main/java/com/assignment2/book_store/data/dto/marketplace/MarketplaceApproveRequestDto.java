package com.assignment2.book_store.data.dto.marketplace;

import lombok.Data;

@Data
public class MarketplaceApproveRequestDto {

    private Long premarketplaceId;
    private Integer qualityId;
    private Float price;

}
