package com.nft;
import com.trade.model.dto.NftDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class NftService {

    private final NftRepository nftRepository;
    private final NftMapper nftMapper;

    public Set<NftDTO> getNftsForCoin(Long coinId) {
        return nftRepository.findAllByCoin_Id(coinId).stream()
                .map(nftMapper::toDto)
                .collect(toSet());
    }
}
