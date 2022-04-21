package com.lab4.demo.item;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        return itemService.findAll();
    }

    @GetMapping(EXPORT_REPORT)
    //@DeleteMapping
    public String exportReport(@PathVariable String type, HttpServletResponse response) throws IOException {
        ReportType reportType = ReportType.valueOf(type);


        return reportServiceFactory.getReportService(reportType).export(response);
    }

    @GetMapping(ID)
    public ItemDto findById(@PathVariable Long id){
        return itemService.findById(id);
    }

    @PostMapping
    public ItemDto create(@RequestBody ItemDto item) {
        return itemService.addItem(item);
    }

    @PatchMapping(ITEMS_ID_PART)
    public ItemDto edit(@PathVariable Long id, @RequestBody ItemDto item) {
        return itemService.edit(id, item);
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Long> delete(@PathVariable Long id) {

        var isRemoved = itemService.delete(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
