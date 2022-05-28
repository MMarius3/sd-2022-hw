package com.coin;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.TickerStatistics;
import com.coin.model.Coin;
import com.coin.model.dto.CoinDTO;
import com.coin.model.dto.CoinFilterRequestDto;
import com.coin.model.mongo.CoinMongoDB;
import com.db.MongoDBService;
import com.email.EmailSenderService;
import com.ws.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoinService {

    private final CoinRepository coinRepository;
    private final CoinMapper coinMapper;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private WebSocketService webSocketService;

    private Coin findById(Long id) {
        return coinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coin not found: " + id));
    }

    public float getPrice(String pairName) throws IOException {

        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
        BinanceApiRestClient client = factory.newRestClient();
        TickerStatistics tickerStatistics = client.get24HrPriceStatistics(pairName);

        return Float.parseFloat(tickerStatistics.getLastPrice());
    }

    private CoinDTO updatePrice(CoinDTO coin) throws IOException {
        coin.setPrice(getPrice(coin.getPair()));
        return coin;
    }

    public void updateAll() throws IOException {
        List<Coin> coins;
        coins = coinRepository.findAll();
        for (Coin coin : coins) {
            coinRepository.save(coinMapper.fromDto(updatePrice(coinMapper.toDto(coin))));
        }
    }

    public List<CoinDTO> findAll() {
        return coinRepository.findAll().stream()
                .map(coinMapper::toDto)
                .collect(Collectors.toList());
    }

    public void sendMail(CoinDTO coin) throws IOException {
        coin.setPrice(getPrice(coin.getPair()));
        emailSenderService.sendEmail("yommane@gmail.com", "New Coin", "Coin" + coin.getPair() + "was just added to the website!");
    }

    public void sendNotification(CoinDTO coin) {
        webSocketService.notifyFrontend(coin.getPair() + " was just added!");
    }

    public void saveToMongoDB(CoinDTO coin) {
        CoinMongoDB coinMongoDB = new CoinMongoDB();
        coinMongoDB.setPair(coin.getPair());
        coinMongoDB.setTop(coin.getTop());
        MongoDBService mongoDB = new MongoDBService();
        mongoDB.insertToMongo(coinMongoDB);
    }

    public CoinDTO create(CoinDTO coin) throws IOException {
        CoinDTO newCoin = new CoinDTO();
        newCoin.setTop(coin.getTop());
        newCoin.setPair(coin.getPair());
        newCoin.setPrice(3);
        return coinMapper.toDto(coinRepository.save(
                coinMapper.fromDto(newCoin)
        ));
    }

    public CoinDTO edit(Long id, CoinDTO coin) throws IOException {
        Coin actCoin = findById(id);
        actCoin.setPair(coin.getPair());
        actCoin.setPrice(getPrice(coin.getPair()));
        actCoin.setTop(coin.getTop());
        return coinMapper.toDto(
                coinRepository.save(actCoin)
        );
    }

    public void delete(Long id) {
        coinRepository.deleteById(id);
    }

    public Page<CoinDTO> findAllFiltered(CoinFilterRequestDto filter, Pageable pageable) {
        return coinRepository.findAll(
                CoinSpecifications.specificationsFromFilter(filter), pageable
        ).map(coinMapper::toDto);
    }
}
