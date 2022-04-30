package com.example.demo.item.crud;

import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDTO toDto(Item item);

    Item fromDto(ItemDTO item);

}