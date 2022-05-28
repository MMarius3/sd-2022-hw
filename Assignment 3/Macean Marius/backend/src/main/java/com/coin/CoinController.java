package com.coin;

import com.UrlMapping;
import com.coin.model.dto.CoinDTO;
import com.coin.model.dto.CoinFilterRequestDto;
import com.report.ReportServiceFactory;
import com.trade.TradeService;
import com.trade.model.dto.TradeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.report.ReportType.CSV;
import static com.report.ReportType.PDF;

@RestController
@RequestMapping(UrlMapping.COINS)
@RequiredArgsConstructor
public class CoinController {

    private final CoinService coinService;
    private final ReportServiceFactory reportServiceFactory;
    private final TradeService tradeService;

    @GetMapping
    public List<CoinDTO> allCoins() {
        return coinService.findAll();
    }

    @GetMapping(UrlMapping.FILTERED)
    public Page<CoinDTO> filteredCoins(@ModelAttribute("filter") CoinFilterRequestDto filter, @PageableDefault(sort
            = {"name"}) Pageable pageable) {
        return coinService.findAllFiltered(filter, pageable);
    }

    @PostMapping
    public CoinDTO create(@RequestBody CoinDTO coin) throws IOException {
        coinService.saveToMongoDB(coin);
        coinService.sendMail(coin);
        coinService.sendNotification(coin);
        return coinService.create(coin);
    }

    @PatchMapping(UrlMapping.ENTITY)
    public CoinDTO edit(@PathVariable Long id, @RequestBody CoinDTO coin) throws IOException {
        return coinService.edit(id, coin);
    }

    @DeleteMapping(UrlMapping.ENTITY)
    public void delete(@PathVariable Long id) {
        coinService.delete(id);
    }

    @GetMapping(UrlMapping.ENTITY + UrlMapping.TRADES)
    public Set<TradeDTO> tradesForCoin(@PathVariable Long id) {
        return tradeService.getTradesForCoin(id);
    }

    @GetMapping(UrlMapping.EXPORT_REPORT)
    public String exportReport(@PathVariable Long id) {
        System.out.println("id: " + id);
        if (id == 0) {
            return reportServiceFactory.getReportService(PDF).export();
        }
        if (id == 1) {
            System.out.println(reportServiceFactory.getReportService(CSV).export());
            return reportServiceFactory.getReportService(CSV).export();
        }
        return null;
    }
}
