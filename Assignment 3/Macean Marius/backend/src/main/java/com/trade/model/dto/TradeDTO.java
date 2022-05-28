package com.trade.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TradeDTO {
    private Long id;
    private String text;
    private String trader;
}
