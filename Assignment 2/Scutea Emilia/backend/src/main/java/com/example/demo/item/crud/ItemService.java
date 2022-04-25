package com.example.demo.item.crud;

import com.example.demo.item.crud.ItemMapper;
import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ReportServiceFactory reportServiceFactory;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO create(ItemDTO item) {
        return itemMapper.toDto(itemRepository.save(
                itemMapper.fromDto(item)
        ));
    }

    public ItemDTO edit(Long id, ItemDTO item) {
        Item actItem = findById(id);
        actItem.setTitle(item.getTitle());
        actItem.setAuthor(item.getAuthor());
        actItem.setGenre(item.getGenre());
        actItem.setQuantity(item.getQuantity());
        actItem.setPrice(item.getPrice());
        return itemMapper.toDto(
                itemRepository.save(actItem)
        );
    }

    public boolean delete(Long id){
       Optional<Item> item = itemRepository.findById(id);
        item.ifPresent(itemRepository::delete);
        return item.isPresent();
    }

    public String export(ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }
}
