package com.example.demo.services;

import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.LabelRepository;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;
    private ImaggaAPI imaggaAPI;
    private LabelRepository labelRepository;
    private ImageService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ImageService(imageRepository, imaggaAPI, labelRepository);
    }
    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }
    @Test
    @Disabled
    void uploadImage() {
    }

    @Test
    void getAllImages() {
        //when
        underTest.getAllImages();
        //then
        verify(imageRepository).findAll();
    }

    @Test
    @Disabled
    void getImagesByLabels() {
    }

    @Test
    void getImageById() {
        //given
        Long imageId = 1L;
        //when
        underTest.getImageById(imageId);
        //then
        verify(imageRepository).findById(imageId);
    }
}