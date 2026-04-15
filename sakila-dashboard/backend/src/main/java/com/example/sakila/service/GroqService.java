package com.example.sakila.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String groqApiKey;

    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

    public String chat(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", userMessage);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "openai/gpt-oss-20b");
        requestBody.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_API_URL, request, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> apiMessage = (Map<String, Object>) choices.get(0).get("message");
                    return (String) apiMessage.get("content");
                }
            }
            return "Sorry, I couldn't generate a response.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with Groq API: " + e.getMessage();
        }
    }
}
