package com.assignment2.book_store.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ImageService {

    public byte[] getImageByName() {
        try {
            InputStream inputStream = new FileInputStream("./images/book.jpg");
            byte[] result = inputStream.readAllBytes();
            inputStream.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
