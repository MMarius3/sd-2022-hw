package com.trade.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NftDTO {
    private Long id;
    private String name;
    private String owner;
    private String image;
}
