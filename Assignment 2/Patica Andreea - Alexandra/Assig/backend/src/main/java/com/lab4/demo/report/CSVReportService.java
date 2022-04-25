package com.lab4.demo.report;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.Item;
import com.lowagie.text.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lab4.demo.report.ReportType.CSV;

@Service
@AllArgsConstructor
public class CSVReportService implements ReportService {
    private final ItemService itemService;

    @Override
    public byte[] export(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/csv" );
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Item> itemList = itemService.findByQuantity(0);
        List<String[]> dataLines = new ArrayList<>();
        writeData(itemList, dataLines);

        File csvOutputFile = new File("report" +currentDateTime+".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }catch(Exception e){
            System.out.println(e);
        }


        return dataLines.stream()
                .map(line -> String.join(", ",line))
                .collect(Collectors.joining("\n"))
                .getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public ReportType getType() {
        return CSV;
    }

    private void writeData(List<Item> itemList, List<String[]> dataLines){
        dataLines.add(new String[]{
                "Title","Author","Price",
        });

        for(Item item: itemList){
            dataLines.add(new String[]
            {
                item.getTitle(), item.getAuthor(), item.getPrice().toString(),
            });
        }
    }

    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
