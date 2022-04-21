package com.lab4.demo.item;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

//better dtos than simple entities
@RestController   //trebuie sa se ocupe de crearea contextului (rest => protocol de comunicare prin json)
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<Item> allItems() {
        System.out.println("findAll");
        for(Item item : itemService.findAll()){
            System.out.println(item.getTile());
        }
        return itemService.findAll();
    }

    @GetMapping(EXPORT_REPORT)
    //@PostMapping
    //@PutMapping
    //@PatchMapping
    //@DeleteMapping
    public String exportReport(@PathVariable ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }

    @GetMapping(ID)
    public ItemDto findById(@PathVariable Long id){
        return itemService.findById(id);
    }

    @GetMapping(ADD_ITEM)
    public boolean addItem(@RequestParam ItemDto itemDto){
        System.out.println(itemDto);
        return itemService.addItem(itemDto);
    }
}
