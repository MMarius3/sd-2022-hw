package com.coin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
public class UpdateCoinsService extends TimerTask {

    private final CoinRepository coinRepository;
    private final CoinMapper coinMapper;

    @Override
    public void run() {
        CoinService coinService = new CoinService(coinRepository, coinMapper);
        try {
            coinService.updateAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
