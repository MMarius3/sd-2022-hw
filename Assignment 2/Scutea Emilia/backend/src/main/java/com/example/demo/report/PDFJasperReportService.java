package com.example.demo.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.SQLException;

@Service
public class PDFJasperReportService implements ReportService{
    @Override
    public String export() {
        try{
            InputStream employeeReportStream
                    = getClass().getResourceAsStream("src/main/jasperReport.jrxml");
            JasperReport jasperReport
                    = JasperCompileManager.compileReport(employeeReportStream);

            JRSaver.saveObject(jasperReport, "booksReport.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, null, new EmbeddedDatabaseBuilder()
                            .setType(EmbeddedDatabaseType.HSQL)
                            .addScript("classpath:bookstore .sql")
                            .build().getConnection());

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("jasperBookReport.pdf"));

            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig
                    = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor("baeldung");
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);

            exporter.exportReport();

        } catch(JRException | SQLException e){
            e.printStackTrace();
        }

        return "I am a Jasper reporter.";
    }

    @Override
    public ReportType getType() {
        return ReportType.PDFJasper;
    }


}
