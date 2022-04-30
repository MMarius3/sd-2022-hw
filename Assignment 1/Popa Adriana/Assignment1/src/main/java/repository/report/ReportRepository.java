package repository.report;

import model.Report;

import java.sql.Date;
import java.util.List;

public interface ReportRepository {

    boolean addReport(Report report);

    List<Report> getReports(Date from, Date until);
}
