package com.example.demo.RestAssuredTests;

import com.example.demo.URLTemplate;
import com.example.demo.models.Image;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.example.demo.RestAssuredTests.actors.Actors;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class FunctionalTests extends BaseTest {
    Actors actor = new Actors(reqSpec);

    @Test
    public void testGetImagesWithoutLabels200() {
        given()
                .spec(reqSpec)
                .when()
                .get(URLTemplate.BasicURLTemplate)
                .then()
                .assertThat()
                .statusCode(200)
                .body("labels", notNullValue());
    }

    @Test
    public void testGetImagesWithoutLabelsNegative() {
        given()
                .spec(reqSpec)
                .when()
                .get("/wrong/endpoint")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testGetImagesWithLabels200() {
        given()
                .spec(reqSpec)
                .param("food")
                .when()
                .get(URLTemplate.BasicURLTemplate)
                .then()
                .assertThat()
                .statusCode(200)
                .body("labels", notNullValue());
    }

    @Test
    public void testGetImagesWithLabelsNegative() {
        given()
                .spec(reqSpec)
                .param("labels", "notExistsingLabel,label2")
                .when()
                .get(URLTemplate.BasicURLTemplate)
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testGetImageById200() {
        given()
                .spec(reqSpec)
                .when()
                .get(URLTemplate.BasicURLTemplate + "/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("labels", notNullValue());
    }

    @Test
    public void testGetImageByIdNegative() {
        given()
                .spec(reqSpec)
                .when()
                .get(URLTemplate.BasicURLTemplate + "/100000")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testGetJsonSchema() {
        given()
                .spec(reqSpec)
                .when()
                .get(URLTemplate.BasicURLTemplate)
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/JSONSchemas/ImagesJsonSchema.json")));
    }

    @Test
    public void testPostIssue201() {
        String imageUrl = "https://cdn.pixabay.com/photo/2018/11/17/22/15/trees-3822149_960_720.jpg";
        actor.createImage(imageUrl);

        List<Image> images = actor.getImages();
        boolean imageExists = actor.checkImageExists(images, imageUrl);
        Assertions.assertTrue(imageExists, "The added image was not found in the list of images.");

        Long imageToDeleteId = actor.getImageId(images, imageUrl);
        actor.deleteImageById(imageToDeleteId);
    }

    @Test
    public void testPostImageWithInvalidImageUrl() {
        JSONObject postParams = new JSONObject();
        try {
            postParams.put("url", "");
            postParams.put("uploadedAt", "2023-05-08");
            postParams.put("analysis_service", "Imagga");
            postParams.put("width", 100);
            postParams.put("height", 200);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        given()
                .spec(reqSpec)
                .contentType("application/json")
                .body(postParams.toString())
                .when()
                .post(URLTemplate.BasicURLTemplate)
                .then()
                .assertThat()
                .statusCode(404);
    }
    @Test
    public void testRemoveImageEndpoint_ImageNotFound() {
        Long imageId = 99L;

        given()
                .spec(reqSpec)
                .when()
                .delete(URLTemplate.BasicURLTemplate + imageId)
                .then()
                .statusCode(404);
    }
}
