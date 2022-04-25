package com.assignment2.book_store.controller;

import com.assignment2.book_store.service.ImageService;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.assignment2.book_store.UrlMapping.IMAGES;

@RestController
@RequestMapping(IMAGES)
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public byte[] getImageByName() {
        return imageService.getImageByName();
    }

}
