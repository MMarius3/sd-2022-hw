package com.example.demo.report;

import com.example.demo.item.crud.ItemMapper;
import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public List<ItemDTO> findItemsByQuantityEquals(Integer quantity){
        return itemRepository.findItemsByQuantityEquals(quantity).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());    }
}
