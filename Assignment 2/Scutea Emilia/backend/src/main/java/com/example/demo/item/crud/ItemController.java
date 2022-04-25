package com.example.demo.item.crud;

import com.example.demo.item.model.dto.ItemDTO;
import com.example.demo.report.ReportType;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;


@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDTO> allItems() {
        return itemService.findAll();
    }

    @PostMapping
    public ItemDTO create(@RequestBody ItemDTO item) {
        return itemService.create(item);
    }

    @PutMapping(ITEMS_ID_EDIT)
    public ItemDTO edit(@PathVariable Long id, @RequestBody ItemDTO item) {
        return itemService.edit(id, item);
    }

    @DeleteMapping(ITEMS_ID_DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(itemService.delete(id)){
            return ResponseEntity.ok(new MessageResponse(String.format("Book with id %d successfully deleted",id)));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: Book with id %d not found",id)));
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return itemService.export(type);
    }
}