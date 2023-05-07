package com.example.demo.services;

import com.example.demo.models.Image;
import com.example.demo.models.Label;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LabelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    private ImageRepository imageRepository;
    private LabelRepository labelRepository;
    private ImaggaAPI imaggaAPI;
    public ImageService(){}
    @Autowired
    public ImageService(ImageRepository imageRepository, ImaggaAPI imaggaAPI, LabelRepository labelRepository){
        this.imageRepository = imageRepository;
        this.labelRepository = labelRepository;
        this.imaggaAPI = imaggaAPI;
    }
    public String uploadImage(Image image){

        String imageUrl = image.getUrl();
        String jsonResponse = imaggaAPI.categorizeImage(imageUrl);

        List<Label> labels = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(jsonResponse);
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
        }catch (JsonProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }
}
