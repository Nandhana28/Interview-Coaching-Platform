package com.interviewcoaching.controllers;

import com.interviewcoaching.models.AIQuery;
import com.interviewcoaching.services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/query")
    public ResponseEntity<AIQuery> askQuestion(@RequestHeader("Authorization") String token,
                                              @RequestBody Map<String, String> request) {
        String userId = extractUserIdFromToken(token); // Implement this method
        AIQuery response = aiService.getAIResponse(userId, request.get("question"), request.get("category"));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<AIQuery>> getQueryHistory(@RequestHeader("Authorization") String token) {
        String userId = extractUserIdFromToken(token);
        List<AIQuery> history = aiService.getUserQueries(userId);
        return ResponseEntity.ok(history);
    }

    private String extractUserIdFromToken(String token) {
        // Extract user ID from JWT token
        return "user123"; // Mock implementation
    }
}