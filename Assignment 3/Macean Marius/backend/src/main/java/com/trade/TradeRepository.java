package com.trade;

import com.trade.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Set<Trade> findAllByCoin_Id(Long coinId);
}
