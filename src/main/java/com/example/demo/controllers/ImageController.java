package com.example.demo.controllers;

import com.example.demo.models.Image;
import com.example.demo.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/images")

public class ImageController {
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestBody Image image) {
        String response = imageService.uploadImage(image);
        return ResponseEntity.ok(response);
        //return new ResponseEntity<String>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages(@RequestParam(required = false) List<String> labels) {
        try {
            if (labels != null && !labels.isEmpty()) {
                List<Image> images = imageService.getImagesByLabels(labels);
                return ResponseEntity.ok(images);
            }
            List<Image> images = imageService.getAllImages();
            return ResponseEntity.ok(images);
        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Image>> getImageById(@PathVariable(value = "id") Long imageId) {
        try {
            Optional<Image> image =  imageService.getImageById(imageId);
            return ResponseEntity.ok(image);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
