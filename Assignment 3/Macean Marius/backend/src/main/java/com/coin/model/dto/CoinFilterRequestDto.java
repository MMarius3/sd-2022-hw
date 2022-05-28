package com.coin.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class CoinFilterRequestDto {
    @Builder.Default
    private final String name = "";
    @Builder.Default
    private final LocalDateTime createdAfter = null;
    @Builder.Default
    private final Boolean onlyWalletExchange = false;
    @Builder.Default
    private final Boolean withAdminTrade = false;
}
