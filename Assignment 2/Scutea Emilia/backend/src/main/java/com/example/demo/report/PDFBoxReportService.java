package com.example.demo.report;

import com.example.demo.item.model.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static com.example.demo.report.ReportType.PDFBox;

@Service
@RequiredArgsConstructor
public class PDFBoxReportService implements ReportService {
    private final ReportServiceImpl reportServiceImpl;

    @Override
    public String export() {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                content.beginText();
                content.setFont(PDType1Font.TIMES_ROMAN, 12);

                content.setLeading(14.5f);
                content.newLineAtOffset(25, 700);


                List<ItemDTO> items = reportServiceImpl.findItemsByQuantityEquals(0);
                if (!items.isEmpty()) {
                    content.showText("Books out of stock:");
                    content.newLine();
                    Field[] fields = ItemDTO.class.getDeclaredFields();
                    StringBuilder sb = new StringBuilder();
                    for(Field field : fields){
                        sb.append(field.getName()).append(" ");
                    }
                    content.showText(sb.toString());
                    content.newLine();
                    for (ItemDTO item : items) {
                        String line = item.getId() + " " + item.getTitle() + " " + item.getAuthor() + " " + item.getGenre() + " " + item.getPrice();
                        content.showText(line);
                        content.newLine();
                    }
                }
                else{
                    content.showText("No items are out of stock");
                }
                content.endText();
            } catch (IOException e) {
                e.printStackTrace();
            }

            doc.save("src/main/resources/pdfReport.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "I am a PDFBox reporter.";
    }


    @Override
    public ReportType getType() {
        return PDFBox;
    }
}

