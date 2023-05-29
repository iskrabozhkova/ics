package com.example.demo.controllers;

import com.example.demo.models.Image;
import com.example.demo.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/images")
@CrossOrigin("http://localhost:4200")

public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        // Using Objects.requiteNonNull as a workaround because https://github.com/spotbugs/spotbugs/issues/1797
        this.imageService = Objects.requireNonNull(imageService, "image service must not be null");
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestBody Image image) {
        if (image.getUrl() == null || image.getUrl().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String response = imageService.uploadImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages(@RequestParam(required = false) List<String> labels) {
        try {
            if (labels != null && !labels.isEmpty()) {
                List<Image> images = imageService.getImagesByLabels(labels);
                if (images.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return ResponseEntity.ok(images);
            }
            List<Image> images = imageService.getAllImages();
            return ResponseEntity.ok(images);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Image>> getImageById(@PathVariable(value = "id") Long imageId) {
        try {
            Optional<Image> image = imageService.getImageById(imageId);

            if (image.isPresent() && image.get() != null) {
                return ResponseEntity.ok(image);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeImage(@PathVariable(value = "id") Long imageId) {
        boolean isDeletionSuccessful = imageService.deleteById(imageId);
        if (isDeletionSuccessful) {
            return ResponseEntity.ok("Image deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }
    
}
