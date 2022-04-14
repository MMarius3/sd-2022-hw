package service.reports.factory;

import service.reports.model.Type;

public interface IReportsGeneratorFactory {
    void generateReport();
    IReportsGeneratorFactory setReportType(Type reportType);
}
