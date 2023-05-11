package com.example.demo.controllers;

import com.example.demo.models.Image;
import com.example.demo.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/images")

public class ImageController {
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public String uploadImage(@RequestBody Image image) {
        return imageService.uploadImage(image);
    }
    @GetMapping
    public List<Image> getAllImages(){
        return imageService.getAllImages();
    }
}
