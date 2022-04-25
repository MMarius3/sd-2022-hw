package com.assignment2.book_store.helper;

import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Hashing {

    private MessageDigest messageDigest;

    public Hashing() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("MD5");
    }

    public byte[] stringToMD5Bytes(String string) {
        messageDigest.update(string.getBytes());
        return messageDigest.digest();
    }

    public String stringToMD5(String string) {
        messageDigest.update(string.getBytes());
        byte[] digest = messageDigest.digest();
        return DatatypeConverter.printHexBinary(digest)
                .toUpperCase();
    }

}
