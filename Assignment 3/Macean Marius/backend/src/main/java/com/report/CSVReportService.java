package com.report;

import com.coin.CoinService;
import com.coin.model.dto.CoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {

    private final CoinService coinService;

    @Override
    public String export() {
        String file = "";
        List<CoinDTO> coins = coinService.findAll();
        file += "Top, Pair, Price\r\n";
        for (CoinDTO coin : coins) {
            file += coin.getTop() + "," + coin.getPair() + "," + coin.getPrice() + "\r\n";
        }
        return file;
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
