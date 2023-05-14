package com.example.demo.services;

import com.example.demo.models.Image;
import com.example.demo.models.Label;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LabelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private ImageRepository imageRepository;
    private LabelRepository labelRepository;
    private ImaggaAPI imaggaAPI;

    public ImageService() {
    }

    @Autowired
    public ImageService(ImageRepository imageRepository, ImaggaAPI imaggaAPI, LabelRepository labelRepository) {
        this.imageRepository = imageRepository;
        this.labelRepository = labelRepository;
        this.imaggaAPI = imaggaAPI;
    }
    public String uploadImage(Image image) {
        String imageUrl = image.getUrl();

        String jsonResponse = imaggaAPI.categorizeImage(imageUrl);
        String responseMessage = "Error occurred.";

        List<Label> labels = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode responseJson = objectMapper.readTree(jsonResponse);
            if (responseJson.has("result")) {
                JsonNode resultJson = responseJson.get("result");
                JsonNode tagsJson = resultJson.get("tags");
                for (JsonNode tagNode : tagsJson) {
                    JsonNode tagJson = tagNode.get("tag");
                    String labelName = tagJson.get("en").asText();

                    Label label = new Label();
                    label.setName(labelName);
                    labels.add(label);
                }
                image.setLabels(labels);
                //save image to the database
                imageRepository.save(image);
                responseMessage = jsonResponse;
            }else{
                JsonNode errorJson = responseJson.get("error");
                String errorMessage = errorJson.get("message").asText();

                responseMessage = "Error: " + errorMessage;
            }
        } catch (JsonProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

    public Image getImageByUrl(String url) {
        Image image = imageRepository.findByUrl(url);
        if (image != null) {
            return image;
        }
        return null;
    }

    public List<Image> getAllImages() {

        return imageRepository.findAll();
    }

    public List<Image> getImagesByLabels(List<String> labels) {
        List<Label> labelList = new ArrayList<>();
        for (String labelName : labels) {
            List<Label> foundLabels = labelRepository.findByName(labelName);
            labelList.addAll(foundLabels);
        }
        return imageRepository.findByLabelsIn(labelList);
    }

    public Optional<Image> getImageById(Long imageId) {
        return imageRepository.findById(imageId);
//        Optional<Image> image = imageRepository.findById(imageId);
//        if (image.isPresent()) {
//            return new ResponseEntity<>(image.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
