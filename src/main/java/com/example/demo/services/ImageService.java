package com.example.demo.services;

import com.example.demo.models.Image;
import com.example.demo.models.Label;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LabelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final LabelRepository labelRepository;
    private  final ImaggaAPI imaggaAPI;

    public ImageService(ImageRepository imageRepository, LabelRepository labelRepository, ImaggaAPI imaggaAPI) {
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
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode responseObject = objectMapper.readValue(jsonResponse, ObjectNode.class);
                responseObject.set("imageUrl", objectMapper.valueToTree(image.getUrl()));
                responseObject.set("imageId", objectMapper.valueToTree(image.getImageId()));
                responseMessage = objectMapper.writeValueAsString(responseObject);
            }catch(JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            String errorMessage = extractErrorMessageFromJson(jsonResponse);
            responseMessage = "Error: " + errorMessage;
        }

        return responseMessage;
    }
    private String convertImageToJson(Image image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(image);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
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

    public boolean deleteById(Long imageId) {
        if(imageId == null){
            return false;
        }
        boolean exists = imageRepository.existsById(imageId);
        if(!exists){
            return false;
        }
        imageRepository.deleteById(imageId);
        return true;
    }
}
