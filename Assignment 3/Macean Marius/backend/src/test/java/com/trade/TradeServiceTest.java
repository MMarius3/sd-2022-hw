package com.trade;

import com.TestCreationFactory;
import com.coin.CoinMapper;
import com.coin.CoinRepository;
import com.coin.CoinService;
import com.trade.model.Trade;
import com.trade.model.dto.TradeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.TestCreationFactory.randomLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeMapper tradeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tradeService = new TradeService(tradeRepository, tradeMapper);
    }

    @Test
    void getTradesForCoin() {
        int nrElements = new Random().nextInt(10) + 5;
        Long id = randomLong();
        Set<Trade> trades = IntStream.range(0, nrElements).mapToObj(i ->
                TestCreationFactory.newTrade(id)
        ).collect(Collectors.toSet());

        when(tradeRepository.findAllByCoin_Id(id)).thenReturn(trades);

        for (Trade trade : trades) {
            when(tradeMapper.toDto(trade)).thenReturn(TestCreationFactory.mapTrade(trade));
        }

        Set<TradeDTO> all = tradeService.getTradesForCoin(id);

        Assertions.assertEquals(trades.size(), all.size());
    }
}