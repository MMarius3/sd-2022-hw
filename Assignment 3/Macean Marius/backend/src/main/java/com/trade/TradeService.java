package com.trade;

import com.trade.model.dto.TradeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    public Set<TradeDTO> getTradesForCoin(Long coinId) {
        return tradeRepository.findAllByCoin_Id(coinId).stream()
                .map(tradeMapper::toDto)
                .collect(toSet());
    }
}
