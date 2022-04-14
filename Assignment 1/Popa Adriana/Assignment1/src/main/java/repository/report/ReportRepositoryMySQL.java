package repository.report;

import model.Report;
import model.builder.ReportBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryMySQL implements ReportRepository{
    private final Connection connection;

    public ReportRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addReport(Report report) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO report values (null, ?, ?, ?)");
            insertStatement.setString(1, report.getEmployee());
            insertStatement.setString(2, report.getAction());
            insertStatement.setDate(3, report.getDate());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Report> getReports(Date from, Date until) {
        List<Report> reports = new ArrayList<>();
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("Select * from report where date >= ? and date <= ?");
            insertStatement.setDate(1,from);
            insertStatement.setDate(2,until);
            ResultSet rs = insertStatement.executeQuery();

            while (rs.next()) {
                reports.add(getReportFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    private Report getReportFromResultSet(ResultSet rs) throws SQLException {
        return new ReportBuilder()
                .setId(rs.getInt("id"))
                .setEmployee(rs.getString("employee"))
                .setAction(rs.getString("action"))
                .setDate(rs.getDate("date"))
                .build();
    }
}
