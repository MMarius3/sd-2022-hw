package com.lab4.demo.item;

import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository bookRepository;
    private final ItemMapper bookMapper;

    private Item findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<ItemDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO create(ItemDTO item) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(item)
        ));
    }

    public ItemDTO edit(Long id, ItemDTO item) {
        Item actBook = findById(id);
        actBook.setTitle(item.getTitle());
        actBook.setAuthor(item.getAuthor());
        actBook.setGenre(item.getGenre());
        actBook.setQuantity(item.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public ItemDTO sell(Long id, ItemDTO book) {
        Item actBook = findById(id);
        if(book.getQuantity() <= actBook.getQuantity()) {
            actBook.setQuantity(actBook.getQuantity() - book.getQuantity());
        }
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public void delete(Long id) {
        Item actItem = findById(id);
        bookRepository.delete(actItem);
    }

}
