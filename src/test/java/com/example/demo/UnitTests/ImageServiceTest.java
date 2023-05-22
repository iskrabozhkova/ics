package com.example.demo.UnitTests;

import com.example.demo.models.Image;
import com.example.demo.models.Label;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LabelRepository;
import com.example.demo.services.ImageService;
import com.example.demo.services.ImaggaAPI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImaggaAPI imaggaAPI;
    @Mock
    private LabelRepository labelRepository;
    @Mock
    private Image image;
    @InjectMocks
    private ImageService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ImageService(imageRepository, labelRepository, imaggaAPI);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllImages() {
        Image image1 = new Image(1L, "imageUrl1", LocalDate.now(), "analysisService", 800, 600, new ArrayList<>());
        Image image2 = new Image(2L, "imageUrl2", LocalDate.now(), "analysisService", 1000, 900, new ArrayList<>());
        List<Image> expectedImages = Arrays.asList(image1, image2);

        when(imageRepository.findAll()).thenReturn(expectedImages);

        List<Image> actualImages = underTest.getAllImages();

        assertEquals(expectedImages, actualImages);
    }
  @Test
  public void testGetImageById_ImageNotFound() {
      when(imageRepository.findById(1L)).thenReturn(Optional.empty());
      Optional<Image> image = underTest.getImageById(1L);
      assertFalse(image.isPresent(), "No image was found");
  }
    @Test
    void getImagesByLabels() {
        Label label1 = new Label();
        label1.setLabelId(1L);
        label1.setName("label1");

        Label label2 = new Label();
        label2.setLabelId(2L);
        label2.setName("label2");

        List<String> labels = Arrays.asList(label1.getName(), label2.getName());

        Image image1 = new Image(1L, "imageUrl1", LocalDate.now(), "analysisService", 800, 600, Arrays.asList(label1, label2));
        Image image2 = new Image(2L, "imageUrl2", LocalDate.now(), "analysisService", 1000, 900, Arrays.asList(label1));

        List<Image> expectedImages = Arrays.asList(image1, image2);

        when(labelRepository.findByName(label1.getName())).thenReturn(Arrays.asList(label1));
        when(labelRepository.findByName(label2.getName())).thenReturn(Arrays.asList(label2));
        when(imageRepository.findByLabelsIn(Arrays.asList(label1, label2))).thenReturn(expectedImages);

        List<Image> actualImages = underTest.getImagesByLabels(labels);
        assertEquals(expectedImages, actualImages);
    }
    @Test
    void testGetImagesByLabels_WhenNoImagesFound() {
        Label label1 = new Label();
        label1.setLabelId(1L);
        label1.setName("label1");

        Label label2 = new Label();
        label2.setLabelId(2L);
        label2.setName("label2");

        List<String> labels = Arrays.asList(label1.getName(), label2.getName());

        when(labelRepository.findByName(label1.getName())).thenReturn(Arrays.asList(label1));
        when(labelRepository.findByName(label2.getName())).thenReturn(Arrays.asList(label2));
        when(imageRepository.findByLabelsIn(Arrays.asList(label1, label2))).thenReturn(Collections.emptyList());

        List<Image> actualImages = underTest.getImagesByLabels(labels);

        assertTrue(actualImages.isEmpty(), "Images with these labels no found");
    }

    @Test
    void getImageById() {
        Long imageId = 1L;
        Image expectedImage = new Image(imageId, "imageUrl", LocalDate.now(), "analysisService", 800, 200, new ArrayList<>());

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(expectedImage));

        Optional<Image> actualImage = underTest.getImageById(imageId);

        assertEquals(Optional.of(expectedImage), actualImage);
    }
    @Test
    void testGetImageById_WhenImageNotFound() {
        Long imageId = 1L;

        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        Optional<Image> actualImage = underTest.getImageById(imageId);

        assertFalse(actualImage.isPresent(), "Image with that id not found");
    }
    @Test
    void testUploadImage_WithValidImage_SuccessfulUpload() {
        Image image = new Image(1L, "imageUrl", LocalDate.now(), "analysisService", 800, 200, new ArrayList<>());

        try {
            JSONObject tag1 = new JSONObject();
            tag1.put("confidence", 20);
            tag1.put("tag", new JSONObject().put("en", "mountain"));

            JSONObject tag2 = new JSONObject();
            tag2.put("confidence", 50);
            tag2.put("tag", new JSONObject().put("en", "landscape"));

            JSONArray tagsArray = new JSONArray();
            tagsArray.put(tag1);
            tagsArray.put(tag2);

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("result", new JSONObject().put("tags", tagsArray));


        when(imaggaAPI.categorizeImage(image.getUrl())).thenReturn(jsonResponse.toString());

        String response = underTest.uploadImage(image);

        verify(imaggaAPI).categorizeImage(image.getUrl());
        verify(imageRepository).save(image);
        assertEquals(jsonResponse.toString(), response);
       assertEquals(2, image.getLabels().size());
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testUploadImage_WithEmptyResponse_ErrorResponse() {
        Image image = new Image(1L, "imageUrl", LocalDate.now(), "analysisService", 800, 200, new ArrayList<>());

        String emptyResponse = "{}";

        when(imaggaAPI.categorizeImage(image.getUrl())).thenReturn(emptyResponse);

        String response = underTest.uploadImage(image);

        verify(imaggaAPI).categorizeImage(image.getUrl());
        verify(imageRepository, never()).save(any(Image.class));
        assertEquals("Error: ", response);
    }
}