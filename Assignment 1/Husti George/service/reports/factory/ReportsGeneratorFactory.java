package service.reports.factory;

import repository.ActionRepository;
import service.reports.TXTReportGenerator;
import service.reports.model.Type;

import java.time.LocalDateTime;

public class ReportsGeneratorFactory implements IReportsGeneratorFactory {

    private Type reportType;

    @Override
    public void generateReport() {

        switch (reportType) {
            case TXT:
                new TXTReportGenerator()
                        .setActionRepository(new ActionRepository())
                        .generateReport(LocalDateTime.MIN, LocalDateTime.MAX, 2);
                break;

            default:
                System.out.println("Unknown Report Type");
        }
    }

    @Override
    public IReportsGeneratorFactory setReportType(Type reportType) {
        this.reportType = reportType;
        return this;
    }
}
