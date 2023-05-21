package com.example.demo.RestAssuredTests;

import io.restassured.RestAssured;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static RequestSpecBuilder builder;
    protected static RequestSpecification reqSpec;
    protected static Dotenv dotenv = Dotenv.load();

    @BeforeAll
    public static void setUp() {
        String baseURL = dotenv.get("BASE_URL");
        builder = new RequestSpecBuilder();
        builder.setBaseUri(baseURL);
        builder.addHeader("Accept", "application/json");
        reqSpec = builder.build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
