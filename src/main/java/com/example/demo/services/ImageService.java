package com.example.demo.services;

import com.example.demo.models.Image;
import com.example.demo.models.Label;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LabelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public String categorizeImage(Image image){
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
            } catch (JsonProcessingException | NullPointerException e) {
                e.printStackTrace();
            }
        return jsonResponse;
    }
    public String getImageLabels(Image image){
        List<Label> foundImageLabels = image.getLabels();
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            String json;
            try {
                json = mapper.writeValueAsString(foundImageLabels);
                return json;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        return "";
    }
    public String uploadImage(Image image) {
        String imageUrl = image.getUrl();
        Image foundImage = getImageByUrl(imageUrl);
        //uploaded image not exists in database
        if (foundImage == null) {
            return categorizeImage(image);
        }
        return getImageLabels(foundImage);
    }
    public Image getImageByUrl(String url) {
        Image image = imageRepository.findByUrl(url);
        if (image != null) {
            return image;
        }
        return null;
    }
}
