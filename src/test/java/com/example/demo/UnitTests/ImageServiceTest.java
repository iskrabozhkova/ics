package com.example.demo.UnitTests;

import com.example.demo.models.Image;
import com.example.demo.models.Label;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LabelRepository;
import com.example.demo.services.ImageService;
import com.example.demo.services.ImaggaAPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImaggaAPI imaggaAPI;
    @Mock
    private LabelRepository labelRepository;
    @InjectMocks
    private ImageService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ImageService(imageRepository, imaggaAPI, labelRepository);
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
    void getImageById() {
        Long imageId = 1L;
        Image expectedImage = new Image(imageId, "imageUrl", LocalDate.now(), "analysisService", 800, 200, new ArrayList<>());

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(expectedImage));

        Optional<Image> actualImage = underTest.getImageById(imageId);

        assertEquals(Optional.of(expectedImage), actualImage);
    }
}