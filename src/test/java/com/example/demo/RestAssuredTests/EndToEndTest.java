package com.example.demo.RestAssuredTests;

import com.example.demo.RestAssuredTests.actors.Actors;
import com.example.demo.models.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EndToEndTest extends BaseTest {
    @Test
    public void testIssueE2E() {
        Actors actor = new Actors(reqSpec);
        List<Image> imagesBefore = actor.getImages();
        String url = "https://cdn.pixabay.com/photo/2012/06/19/10/32/owl-50267_960_720.jpg";
        int width = 100;
        int height = 200;
        actor.postImage(url, width, height);
        List<Image> imagesAfter = actor.getImages();
        Assertions.assertEquals(imagesBefore.size() + 1, imagesAfter.size());
        boolean imageExists = false;
        for (Image img : imagesAfter) {
            if (img.getUrl().equals("https://cdn.pixabay.com/photo/2012/06/19/10/32/owl-50267_960_720.jpg")) {
                imageExists = true;
                break;
            }
        }
        Assertions.assertTrue(imageExists, "The added image was not found in the list of images.");
    }
}
