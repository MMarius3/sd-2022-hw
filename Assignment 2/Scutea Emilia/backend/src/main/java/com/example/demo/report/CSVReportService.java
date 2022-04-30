package com.example.demo.report;


import com.example.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.demo.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {

    private final ReportServiceImpl reportServiceImpl;

    @Override
    public String export() {
        try(PrintWriter writer = new PrintWriter("src/main/resources/csvReport.csv")){
            StringBuilder stringBuilder = new StringBuilder();
            List<ItemDTO> items = reportServiceImpl.findItemsByQuantityEquals(0);

            if(!items.isEmpty()){
                for(ItemDTO item : items){
                    stringBuilder
                            .append(item.getId())
                            .append(",")
                            .append(item.getTitle())
                            .append(",")
                            .append(item.getAuthor())
                            .append(",")
                            .append(item.getPrice());
                }
            }

            writer.write(stringBuilder.toString());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
