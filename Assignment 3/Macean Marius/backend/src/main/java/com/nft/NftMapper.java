package com.nft;

import com.nft.model.Nft;
import com.trade.model.dto.NftDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NftMapper {
    NftDTO toDto(Nft nft);
}
