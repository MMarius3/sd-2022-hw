package com.assignment2.book_store.helper;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HashingTest {

    private static final String HELLO_WORLD = "Hello, World!";
    private static final String HELLO_WORLD_ONLINE_GENERATED_MD5 = "65a8e27d8879283831b664bd8b7f0ad4".toUpperCase();
    private static final byte[] HELLO_WORLD_ONLINE_GENERATED_MD5_BYTES = new byte[]{101, -88, -30, 125, -120, 121, 40, 56, 49, -74, 100, -67, -117, 127, 10, -44};

    private Hashing hashing;

    HashingTest() throws NoSuchAlgorithmException {
        hashing = new Hashing();
    }

    @Test
    public void getMD5Bytes() {
        assertArrayEquals(HELLO_WORLD_ONLINE_GENERATED_MD5_BYTES, hashing.stringToMD5Bytes(HELLO_WORLD));
    }

    @Test
    public void getMD5String() {
        assertEquals(HELLO_WORLD_ONLINE_GENERATED_MD5, hashing.stringToMD5(HELLO_WORLD));
    }

}