package com.lab4.demo.report;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ItemService itemService;

    public void export(ReportType type){
        switch (type){
            case PDF:
                generatePDF();
                break;
            case CSV:
                generateCSV();
                break;
        }
    }

    private void generatePDF() {
        List<ItemDTO> items = itemService.findAll();
        try (PDDocument doc = new PDDocument()) {

            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

                cont.beginText();

                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.setLeading(14.5f);

                cont.newLineAtOffset(25, 700);

                for(ItemDTO item: items){
                    if(item.getQuantity() == 0){
                        String line = item.getTitle() + ", " + item.getAuthor() + ", " + item.getGenre();
                        cont.showText(line);
                        cont.newLine();
                    }
                }
                cont.endText();
            }

            doc.save("out_of_stock.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCSV() {
        List<ItemDTO> items = itemService.findAll();
        try (PrintWriter writer = new PrintWriter("out_of_stock.csv")) {
            for(ItemDTO item: items){
                if(item.getQuantity() == 0){
                    StringBuilder sb = new StringBuilder();
                    sb.append(item.getTitle());
                    sb.append(",");
                    sb.append(item.getAuthor());
                    sb.append(",");
                    sb.append(item.getGenre());
                    sb.append("\n");

                    writer.write(sb.toString());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
