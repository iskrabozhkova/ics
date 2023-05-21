package com.example.demo.RestAssuredTests;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class FunctionalTests extends BaseTest {

    @Test
    public void testGetImagesWithoutLabels200() {
        given()
                .spec(reqSpec)
                .when()
                .get("/api/images")
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
                .param("labels", "fractal")
                .when()
                .get("/api/images")
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
                .get("/api/images")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testGetImageById200() {
        given()
                .spec(reqSpec)
                .when()
                .get("/api/images/12")
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
                .get("/api/images/100000")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testGetJsonSchema() {
        given()
                .spec(reqSpec)
                .when()
                .get("/api/images")
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/JSONSchemas/ImagesJsonSchema.json")));
    }

//    @Test
//    @Disabled
//    public void testPostIssue201() {
//        JSONObject postParams = new JSONObject();
//        try {
//            postParams.put("url", "https://cdn.pixabay.com/photo/2018/11/17/22/15/trees-3822149_960_720.jpg");
//            postParams.put("uploadedAt", "2023-05-08");
//            postParams.put("analysis_service", "Imagga");
//            postParams.put("width", 100);
//            postParams.put("height", 200);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        given()
//                .spec(reqSpec)
//                .contentType("application/json")
//                .body(postParams.toString())
//                .when()
//                .post("/api/images")
//                .prettyPeek()
//                .then()
//                .assertThat()
//                .statusCode(201);
//    }

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
                .post("/api/images")
                .then()
                .assertThat()
                .statusCode(404);
    }

}
