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

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof ItemDto)) {
            return false;
        }

        ItemDto c = (ItemDto) o;

        return this.title.equals(((ItemDto) o).getTitle())
                && this.author.equals(((ItemDto) o).getAuthor())
                && this.quantity.equals(((ItemDto) o).getQuantity())
                && this.price.equals(((ItemDto) o).getPrice())
                && this.description.equals(((ItemDto) o).getDescription());
    }
}
