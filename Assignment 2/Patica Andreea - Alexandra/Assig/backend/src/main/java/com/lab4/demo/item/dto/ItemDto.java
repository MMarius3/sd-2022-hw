package com.lab4.demo.item.dto;

import com.lab4.demo.item.model.Item;
import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private String name;
    private String description;

    public static ItemDto toDto(Item item){
        return ItemDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .build();
    }
}
