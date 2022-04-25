package com.assignment2.book_store.helper.report;


import com.assignment2.book_store.service.ReportService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ReportServiceFactory {

    private final Map<ReportType, ReportService> reportServices;

    public ReportServiceFactory(List<ReportService> reportServices) {
        this.reportServices = reportServices.stream()
                .collect(Collectors.toMap(ReportService::getType, Function.identity()));
    }

    public ReportService getReportService(ReportType type) {
        return reportServices.get(type);
    }

}
