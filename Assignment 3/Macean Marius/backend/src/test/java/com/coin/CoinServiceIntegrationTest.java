package com.coin;

import com.TestCreationFactory;
import com.coin.model.Coin;
import com.coin.model.dto.CoinDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class CoinServiceIntegrationTest {

    @Autowired
    private CoinService coinService;

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private CoinMapper coinMapper;

    @BeforeEach
    void setUp() {
        coinRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Coin> coins = TestCreationFactory.listOf(Coin.class);
        coinRepository.saveAll(coins);

        List<CoinDTO> all = coinService.findAll();

        Assertions.assertEquals(coins.size(), all.size());
    }

    @Test
    void updateAll() throws IOException {
        List<CoinDTO> coinsDTO = TestCreationFactory.listOf(CoinDTO.class);
        List<Coin> coins = TestCreationFactory.listOf(Coin.class);
        CoinDTO coinDTO = TestCreationFactory.newCoinDTO();
        Coin coin = TestCreationFactory.mapCoin(coinDTO);

        for (CoinDTO coinDTO1 : coinsDTO) {
            coinDTO1.setPrice(coinService.getPrice(coinDTO1.getPair()));
            coinRepository.save(coinMapper.fromDto(coinDTO1));
        }

        coinService.updateAll();
        for (int i = 0; i < coins.size(); i++) {
            Assertions.assertEquals(coins.get(i).getPair(), coinsDTO.get(i).getPair());
            Assertions.assertEquals(coins.get(i).getPrice(), coinsDTO.get(i).getPrice());
        }
    }

    @Test
    void create() throws IOException {
        CoinDTO coinDTO = TestCreationFactory.newCoinDTO();
        Coin coin = TestCreationFactory.mapCoin(coinDTO);

        coinRepository.save(coin);
        coinService.create(coinDTO);
        Assertions.assertEquals(coinDTO.getPair(), coin.getPair());
    }
}