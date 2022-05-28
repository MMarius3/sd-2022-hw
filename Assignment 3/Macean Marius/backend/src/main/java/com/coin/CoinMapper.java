package com.coin;

import com.coin.model.Coin;
import com.coin.model.dto.CoinDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CoinMapper {

    CoinDTO toDto(Coin coin);

    @Mappings({
            @Mapping(target = "trades", ignore = true)
    })
    Coin fromDto(CoinDTO coin);
}
