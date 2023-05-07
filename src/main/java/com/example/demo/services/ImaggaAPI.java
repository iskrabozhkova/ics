package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ImaggaAPI {
    private static final String API_KEY = "acc_e978d85bb0300de";
    private static final String API_SECRET = "cb2a812cb9772d0e275faab050e8f830";

    public String categorizeImage(String imageUrl) {
        try {
            String credentialsToEncode = API_KEY + ":" + API_SECRET;
            String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

            String endpointUrl = "https://api.imagga.com/v2/tags";
            String url = endpointUrl + "?image_url=" + imageUrl;
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

            connection.setRequestProperty("Authorization", "Basic " + basicAuth);

            int responseCode = connection.getResponseCode();

            StringBuilder jsonResponse = new StringBuilder();
            try (BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = connectionInput.readLine()) != null) {
                    jsonResponse.append(line);
                }
            }
            return jsonResponse.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}