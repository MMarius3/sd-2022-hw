package com.lab4.demo.item;

import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.report.ReportService;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ReportService reportService;

    @CrossOrigin
    @GetMapping
    public List<ItemDTO> allItems() {
        return itemService.findAll();
    }

    @PostMapping(ITEMS_CREATE)
    public ItemDTO create(@RequestBody ItemDTO book) {
        return itemService.create(book);
    }

    @CrossOrigin
    @PatchMapping(SELL_ITEM)
    public ItemDTO sell(@PathVariable Long id, @RequestBody ItemDTO item) {
        return itemService.sell(id, item);
    }

    @PatchMapping(ITEMS_ID_PART)
    public ItemDTO edit(@PathVariable Long id, @RequestBody ItemDTO item) {
        return itemService.edit(id, item);
    }

    @PostMapping(REPORT)
    public void export(@PathVariable ReportType type) {
        reportService.export(type);
    }

    @DeleteMapping(DELETE_ITEM)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
