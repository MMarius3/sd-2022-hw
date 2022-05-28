package com.nft;

import com.TestCreationFactory;
import com.nft.model.Nft;
import com.trade.TradeMapper;
import com.trade.TradeRepository;
import com.trade.TradeService;
import com.trade.model.Trade;
import com.trade.model.dto.NftDTO;
import com.trade.model.dto.TradeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.TestCreationFactory.randomLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class NftServiceTest {

    @InjectMocks
    private NftService nftService;

    @Mock
    private NftRepository nftRepository;

    @Mock
    private NftMapper nftMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        nftService = new NftService(nftRepository, nftMapper);
    }

    @Test
    void getNftsForCoin() {
        int nrElements = new Random().nextInt(10) + 5;
        Long id = randomLong();
        Set<Nft> nfts = IntStream.range(0, nrElements - 1).mapToObj(i ->
                TestCreationFactory.newNft(id)
        ).collect(Collectors.toSet());

        when(nftRepository.findAllByCoin_Id(id)).thenReturn(nfts);

        for (Nft nft : nfts) {
            when(nftMapper.toDto(nft)).thenReturn(TestCreationFactory.mapNft(nft));
        }

        Set<NftDTO> all = nftService.getNftsForCoin(id);

        Assertions.assertEquals(nfts.size(), all.size());
    }
}