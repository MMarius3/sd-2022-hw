package com.example.demo;

import com.example.demo.item.model.Item;
import com.example.demo.item.model.GenreType;
import com.example.demo.item.model.dto.ItemDTO;
import com.example.demo.user.dto.UserDTO;

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

        if (cls.equals(UserDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Item.class)) {
            supplier = TestCreationFactory::newItem;
        } else if (cls.equals(ItemDTO.class)) {
            supplier = TestCreationFactory::newItemDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserDTO newUserListDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .build();
    }

    private static Item newItem() {
        return Item.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre("Comedy")
                .quantity(randomBoundedInt(100))
                .price(10.0)
                .build();
    }

    private static ItemDTO newItemDTO() {
        return ItemDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre("Comedy")
                .quantity(randomBoundedInt(100))
                .price(10.0)
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