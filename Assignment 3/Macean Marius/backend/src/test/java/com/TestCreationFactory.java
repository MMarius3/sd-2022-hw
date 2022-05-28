package com;

import com.coin.model.Coin;
import com.coin.model.dto.CoinDTO;
import com.coin.model.mongo.CoinMongoDB;
import com.nft.model.Nft;
import com.trade.model.Trade;
import com.trade.model.dto.NftDTO;
import com.trade.model.dto.TradeDTO;
import com.user.dto.UserListDTO;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Coin.class)) {
            supplier = TestCreationFactory::newCoin;
        } else if (cls.equals(CoinDTO.class)) {
            supplier = TestCreationFactory::newCoinDTO;
        } else if (cls.equals(TradeDTO.class)) {
            supplier = TestCreationFactory::newTradeDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    public static Coin mapCoin(CoinDTO coinDTO) {
        Coin coin = Coin.builder()
                .id(coinDTO.getId())
                .pair(coinDTO.getPair())
                .top(coinDTO.getTop())
                .price(coinDTO.getPrice())
                .build();
        return coin;
    }

    private static Coin newCoin() {
        return Coin.builder()
                .id(randomLong())
                .pair("BTCUSDT")
                .top((int)Math.random() * 10)
                .price((int)Math.random() * 10)
                .build();
    }

    public static TradeDTO mapTrade(Trade trade) {
        TradeDTO tradeDTO = TradeDTO.builder()
                .id(trade.getId())
                .text(trade.getText())
                .build();
        return tradeDTO;
    }

    public static NftDTO mapNft(Nft nft) {
        NftDTO nftDTO = NftDTO.builder()
                .id(nft.getId())
                .image(nft.getImage())
                .name(nft.getName())
                .build();
        return nftDTO;
    }

    public static Trade newTrade(Long id) {
        return Trade.builder()
                .id(randomLong())
                .text("good trade")
                .build();
    }

    public static Nft newNft(Long id) {
        return Nft.builder()
                .id(randomLong())
                .name("good trade")
                .image("ape.jpg")
                .build();
    }

    public static CoinMongoDB newCoinMongoDB() {
        return CoinMongoDB.builder()
                .id(randomLong())
                .pair("BTCUSDT")
                .top((int)Math.random() * 10)
                .build();
    }

    public static CoinDTO newCoinDTO() {
        return CoinDTO.builder()
                .id(randomLong())
                .pair("BTCUSDT")
                .top((int)Math.random() * 10)
                .price((int)Math.random() * 10)
                .build();
    }

    private static TradeDTO newTradeDTO() {
        return TradeDTO.builder()
                .id(randomLong())
                .text(randomString())
                .trader(randomString())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
