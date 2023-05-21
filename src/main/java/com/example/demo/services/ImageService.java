package com.example.demo.services;

import com.example.demo.models.Image;
import com.example.demo.models.Label;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LabelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        List<Label> labels = extractLabelsFromJson(jsonResponse);
        if (!labels.isEmpty()) {
            image.setLabels(labels);
            imageRepository.save(image);
            responseMessage = jsonResponse;
        } else {
            String errorMessage = extractErrorMessageFromJson(jsonResponse);
            responseMessage = "Error: " + errorMessage;
        }

        return responseMessage;
    }

    public List<Label> extractLabelsFromJson(String jsonResponse) {
        List<Label> labels = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(jsonResponse);

            if (responseJson.has("result")) {
                JsonNode resultJson = responseJson.get("result");
                JsonNode tagsJson = resultJson.get("tags");

                if (tagsJson != null) {
                    for (JsonNode tagNode : tagsJson) {
                        JsonNode tagJson = tagNode.get("tag");

                        if (tagJson != null) {
                            String labelName = tagJson.get("en").asText();

                            Label label = new Label();
                            label.setName(labelName);
                            labels.add(label);
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return labels;
    }

    public String extractErrorMessageFromJson(String jsonResponse) {
        String errorMessage = "";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(jsonResponse);
            JsonNode errorJson = responseJson.get("error");

            if (errorJson != null) {
                errorMessage = errorJson.get("message").asText();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return errorMessage;
    }

//    //private void saveImageToDatabase(Image image) {
//        imageRepository.save(image);
//    }

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
    }

//    public void deleteImageById(Long imageId) {
//        boolean exists = imageRepository.existsById(imageId);
//        if(!exists){
//            throw new IllegalStateException("Image with id " + imageId + " does not exist");
//        }
//        imageRepository.deleteById(imageId);
//    }
}
