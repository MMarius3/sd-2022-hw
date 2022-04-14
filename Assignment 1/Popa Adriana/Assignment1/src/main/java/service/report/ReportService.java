package service.report;

import model.Report;

import java.sql.Date;
import java.util.List;

public interface ReportService {

    boolean addReport(Report report);

    List<Report> getReports(Date from, Date until);
}
