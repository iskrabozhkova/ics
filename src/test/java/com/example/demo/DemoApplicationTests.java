package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
class DemoApplicationTests {

	private static final String BASE_URL = "http://localhost:8080";

	@Before
	public void setup() {
		RestAssured.baseURI = BASE_URL;
	}

	@Test
	public void testUploadImage() {
		String requestBody = "{\n" +
				"\"url\": \"https://www.shutterstock.com/image-photo/aerial-view-sandstone-butte-utah-600w-2261937703.jpg\",\n" +
				"\"uploadedAt\": \"2023-05-08\",\n" +
				"\"analysis_service\": \"Imagga\",\n" +
				"\"width\": 100,\n" +
				"\"height\": 200\n" +
				"}";

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when()
				.post("/api/images")
				.then()
				.statusCode(200);
	}
@Test
public void testGetAllImagesWithoutLabels() {
	given()
			.when()
			.get("/api/images")
			.then()
			.statusCode(200);
	}
	@Test
	public void testGetAllImagesWithLabels() {
		given()
				.param("labels", "label1,label2")
				.when()
				.get("/api/images")
				.then()
				.statusCode(200);
	}
	@Test
	public void testGetImageById() {
		Long imageId = 1L;

		given()
				.pathParam("id", imageId)
				.when()
				.get("/api/images/{id}")
				.then()
				.statusCode(200);
	}
}

