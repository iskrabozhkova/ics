package com.example.demo.RestAssuredTests.actors;

import com.example.demo.URLTemplate;
import com.example.demo.models.Image;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;


public class Actors {
    private RequestSpecification spec;

    public Actors(RequestSpecification reqSpec) {
        this.spec = reqSpec;
    }

    public List<Image> getImages() {
        List<Image> images = RestAssured.given()
                .spec(this.spec)
                .when()
                .get(URLTemplate.BasicURLTemplate)
                .jsonPath().getList("", Image.class);
        return images;
    }
    public void deleteImageById(Long imageId){
        String imageUrl = URLTemplate.SingleImageURLTemplate.replace("{id}", String.valueOf(imageId));
        RestAssured.given()
                .spec(this.spec)
                .when()
                .delete(imageUrl);
    }
    public Response postImage(String imageUrl, int width, int height) {
        JSONObject postParams = new JSONObject();
        try {
            postParams.put("url", imageUrl);
            postParams.put("uploadedAt", "2023-05-08");
            postParams.put("analysis_service", "Imagga");
            postParams.put("width", width);
            postParams.put("height", height);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Response response = given()
                .spec(this.spec)
                .contentType("application/json")
                .body(postParams.toString())
                .when()
                .post(URLTemplate.BasicURLTemplate)
                .prettyPeek();
        return response;
    }
    public void createImage(String imageUrl) {
        JSONObject postParams = new JSONObject();
        try {
            postParams.put("url", imageUrl);
            postParams.put("uploadedAt", "2023-05-08");
            postParams.put("analysis_service", "Imagga");
            postParams.put("width", 100);
            postParams.put("height", 200);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        given()
                .spec(spec)
                .contentType("application/json")
                .body(postParams.toString())
                .when()
                .post("/api/images")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(201);
    }
    public boolean checkImageExists(List<Image> images, String imageUrl) {
        for (Image img : images) {
            if (img.getUrl().equals(imageUrl)) {
                return true;
            }
        }
        return false;
    }

    public Long getImageId(List<Image> images, String imageUrl) {
        for (Image img : images) {
            if (img.getUrl().equals(imageUrl)) {
                return img.getImageId();
            }
        }
        return null;
    }
}
