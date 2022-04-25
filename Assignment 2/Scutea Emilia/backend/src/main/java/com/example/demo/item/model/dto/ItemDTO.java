package com.example.demo.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Integer quantity;
    private Double price;
}
