package com.example.demo.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ImaggaAPI {
    private static final String API_KEY = "acc_e978d85bb0300de";
    private static final String API_SECRET = "cb2a812cb9772d0e275faab050e8f830";

    private WebClient webClient;

    public ImaggaAPI() {
        this.webClient = WebClient.create();
    }

    public String categorizeImage(String imageUrl) {
        try {
            String endpointUrl = "https://api.imagga.com/v2/tags";
            String url = endpointUrl + "?image_url=" + imageUrl;

            return webClient.method(HttpMethod.GET)
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, createAuthorizationHeader())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String createAuthorizationHeader() {
        String credentialsToEncode = API_KEY + ":" + API_SECRET;
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));
        return "Basic " + basicAuth;
    }
}