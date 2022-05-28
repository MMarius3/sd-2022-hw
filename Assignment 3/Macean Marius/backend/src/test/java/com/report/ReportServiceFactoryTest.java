package com.report;

import com.coin.CoinService;
import com.coin.model.dto.CoinDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private CoinService coinService;

    @Test
    void getReportService() {
        String file = "";
        List<CoinDTO> coins = coinService.findAll();
        file += "Top, Pair, Price\r\n";
        for (CoinDTO coin : coins) {
            file += coin.getTop() + "," + coin.getPair() + "," + coin.getPrice() + "\r\n";
        }

        ReportService csvReportService = reportServiceFactory.getReportService(ReportType.CSV);
        Assertions.assertEquals(file, csvReportService.export());

        ReportService pdfReportService = reportServiceFactory.getReportService(ReportType.PDF);
        Assertions.assertEquals("I am a PDF reporter.", pdfReportService.export());
    }
}