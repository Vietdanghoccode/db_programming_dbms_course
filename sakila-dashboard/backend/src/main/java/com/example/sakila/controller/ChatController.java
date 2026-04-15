package com.example.sakila.controller;

import com.example.sakila.service.GroqService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChatController {

    private final GroqService groqService;

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        if (message == null || message.trim().isEmpty()) {
            return Map.of("reply", "Please provide a message.");
        }
        
        String reply = groqService.chat(message);
        return Map.of("reply", reply);
    }
}
