package com.lab4.demo.report;

import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {
    @Override
    public String export(List<String> content) {
        File file = new File("src/report.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = {"Id", "Title", "Author", "Genre", "Quantity", "Price"};
            writer.writeNext(header);
            for(String line:content){
                String[] data = line.split(",");
                writer.writeNext(data);
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "I am a CSV reporter";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
