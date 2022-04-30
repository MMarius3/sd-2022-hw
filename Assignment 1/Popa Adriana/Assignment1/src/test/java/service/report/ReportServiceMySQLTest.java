package service.report;

import database.DatabaseConnectionFactory;
import model.Report;
import model.builder.ReportBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceMySQLTest {

    private static ReportService reportService;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DatabaseConnectionFactory().getConnectionWrapper(true).getConnection();
        ReportRepository reportRepository = new ReportRepositoryMySQL(connection);
        reportService = new ReportServiceMySQL(reportRepository);
    }

    @Test
    void addReport() {
        Report report = new ReportBuilder()
                .setEmployee("byeee@mail.com")
                .setAction("deleted client 1!")
                .setDate(Date.valueOf(LocalDate.now()))
                .build();
        boolean added = reportService.addReport(report);
        assertTrue(added);
    }

    @Test
    void getReports() {
        Report report1 = new ReportBuilder()
                .setEmployee("byeee@mail.com")
                .setAction("deleted client 1!")
                .setDate(Date.valueOf(LocalDate.now()))
                .build();
        Report report2 = new ReportBuilder()
                .setEmployee("byeee@mail.com")
                .setAction("deleted client 1!")
                .setDate(Date.valueOf("2022-02-18"))
                .build();
        List<Report> reports = reportService.getReports(Date.valueOf("2022-02-18"),Date.valueOf("2022-03-19"));
        assertEquals(15,reports.size());
    }
}