package com.lab4.demo.item;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<Item> allItems() {
        return itemService.findAll();
    }

    @GetMapping(EXPORT_REPORT)
    public byte[] exportReport(@PathVariable String type, HttpServletResponse response) throws IOException {
        ReportType reportType = ReportType.valueOf(type);

        return reportServiceFactory.getReportService(reportType).export(response);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ItemDto create(@RequestBody ItemDto item) {
        return itemService.addItem(item);
    }

    @PatchMapping(ITEMS_ID_PART)
    public ItemDto edit(@PathVariable Long id, @RequestBody ItemDto item) {
        return itemService.edit(id, item);
    }

    @DeleteMapping(DELETE)
    public void delete(@PathVariable Long id) {

        itemService.delete(id);

//        if (!isRemoved) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
