package com.lab4.demo.item.dto;

import com.lab4.demo.item.model.Item;
import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private String title;
    private String author;
    private Integer price;
    private Integer quantity;
    private String description;

    public static ItemDto toDto(Item item){
        return ItemDto.builder()
                .title(item.getTitle())
                .author(item.getAuthor())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .description(item.getDescription())
                .build();
    }
}
