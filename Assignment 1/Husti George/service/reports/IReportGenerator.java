package service.reports;

import repository.ActionRepository;

import java.time.LocalDateTime;

public interface IReportGenerator {
    void generateReport(LocalDateTime startDate, LocalDateTime endDate, int userID);
    IReportGenerator setActionRepository(ActionRepository actionRepository);
}
