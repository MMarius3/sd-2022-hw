package com.trade;

import com.trade.model.Trade;
import com.trade.model.dto.TradeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    @Mappings({
            @Mapping(target = "trader", source = "trade.user.email")
    })
    TradeDTO toDto(Trade trade);
}
