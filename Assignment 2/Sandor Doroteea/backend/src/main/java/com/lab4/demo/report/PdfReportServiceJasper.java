package com.lab4.demo.report;

import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lab4.demo.report.ReportType.PDF;

@RequiredArgsConstructor
@Service
public class PdfReportServiceJasper implements ReportService {

    private final BookRepository bookRepository;
    static final String fileName = "src/main/resources/JasperDesign.jrxml";
    static final String outFile = "src/main/resources/ReportsJasper.pdf";

    @Override
    public String export() {
        List<Book> booksOutOfStock = bookRepository
                .findAll()
                .stream()
                .filter(book -> book.getQuantity() == 0)
                .collect(Collectors.toList());
        Map<String, Object> parameter  = new HashMap<String, Object>();
        JRBeanCollectionDataSource booksCollectionDataSource =
                new JRBeanCollectionDataSource(booksOutOfStock);
        parameter.put("booksDataSource", booksCollectionDataSource);

        //parameter.put("title", new String("Hi, I am Title"));

        JasperReport jasperDesign = null;
        try {
            jasperDesign = JasperCompileManager.compileReport(fileName);
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperDesign, parameter,
                    new JREmptyDataSource());
        } catch (JRException e) {
            e.printStackTrace();
        }

        File file = new File(outFile);
        OutputStream outputSteam = null;
        try {
            outputSteam = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputSteam);
        } catch (JRException e) {
            e.printStackTrace();
        }
        return "PDF Jasper report generated";
    }

    @Override
    public ReportType getType() {
        //return PDF;
        return null;
    }
}
