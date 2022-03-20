package model.builder;

import model.Report;

import java.sql.Date;

public class ReportBuilder {
    private final Report report;

    public ReportBuilder(){
        report = new Report();
    }

    public ReportBuilder setId(Integer id){
        report.setId(id);
        return this;
    }

    public ReportBuilder setEmployee(String employee){
        report.setEmployee(employee);
        return this;
    }

    public ReportBuilder setAction(String action){
        report.setAction(action);
        return this;
    }

    public ReportBuilder setDate(Date date){
        report.setDate(date);
        return this;
    }

    public Report build(){
        return report;
    }
}
