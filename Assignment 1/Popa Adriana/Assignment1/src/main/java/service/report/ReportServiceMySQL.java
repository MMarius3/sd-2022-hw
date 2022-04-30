package service.report;

import model.Report;
import repository.report.ReportRepository;

import java.sql.Date;
import java.util.List;

public class ReportServiceMySQL implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceMySQL(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    @Override
    public boolean addReport(Report report) {
        return reportRepository.addReport(report);
    }

    @Override
    public List<Report> getReports(Date from, Date until) {
        return reportRepository.getReports(from,until);
    }
}
