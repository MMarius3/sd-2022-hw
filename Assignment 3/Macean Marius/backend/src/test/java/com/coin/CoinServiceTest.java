package com.coin;

import com.TestCreationFactory;
import com.coin.model.Coin;
import com.coin.model.dto.CoinDTO;
import com.email.EmailSenderService;
import com.ws.WebSocketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CoinServiceTest {

    @InjectMocks
    private CoinService coinService;

    @Mock
    private CoinRepository coinRepository;

    @Mock
    private CoinMapper coinMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        coinService = new CoinService(coinRepository, coinMapper);
        Coin coin = Coin.builder()
                .pair("ETHUSDT")
                .price(100)
                .top(2)
                .build();
        coinRepository.save(coin);
    }

    @Test
    void findAll() {
        List<Coin> coins = TestCreationFactory.listOf(Coin.class);
        when(coinRepository.findAll()).thenReturn(coins);

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
            coinDTO1.setPrice(100);
        }

        when(coinMapper.toDto(coin)).thenReturn(coinDTO);
        when(coinRepository.findAll()).thenReturn(coins);
        when(coinRepository.save(coin)).thenReturn(coin);

        coinService.updateAll();
        Assertions.assertEquals(coins.size(), coinsDTO.size());
        for (int i = 0; i < coins.size(); i++) {
            Assertions.assertEquals(coins.get(i).getPair(), coinsDTO.get(i).getPair());
            Assertions.assertEquals(coins.get(i).getPrice(), coinsDTO.get(i).getPrice());
        }
    }

    @Test
    void create() throws IOException {
        CoinDTO coinDTO = TestCreationFactory.newCoinDTO();
        Coin coin = TestCreationFactory.mapCoin(coinDTO);

        when(coinRepository.save(coin)).thenReturn(coin);
        coinService.create(coinDTO);
        Assertions.assertEquals(coinDTO.getPair(), coin.getPair());
    }

    @Test
    void edit() throws IOException {
        CoinDTO coinDTO = TestCreationFactory.newCoinDTO();
        Coin coin = TestCreationFactory.mapCoin(coinDTO);

        Long id = coin.getId();

        when(coinRepository.findById(id)).thenReturn(Optional.of(coin));
        when(coinRepository.save(coin)).thenReturn(coin);
        when(coinMapper.toDto(coin)).thenReturn(coinDTO);

        coinDTO.setPair("SOLUSDT");

        CoinDTO resCoinDTO = coinService.edit(id, coinDTO);
        Assertions.assertEquals(coinDTO.getPair(), resCoinDTO.getPair());
    }

    @Test
    void delete() {
        CoinDTO coinDTO = TestCreationFactory.newCoinDTO();
        coinService.delete(coinDTO.getId());
        verify(coinRepository, times(1)).deleteById(coinDTO.getId());
    }

    @Test
    void findAllFiltered() {
        CoinDTO coinDTO = TestCreationFactory.newCoinDTO();
        coinService.delete(coinDTO.getId());
        verify(coinRepository, times(1)).deleteById(coinDTO.getId());
    }
}