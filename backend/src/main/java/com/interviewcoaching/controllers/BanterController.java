package com.interviewcoaching.controllers;

import com.interviewcoaching.models.BanterMessage;
import com.interviewcoaching.services.BanterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/banter")
@CrossOrigin(origins = "http://localhost:3000")
public class BanterController {

    @Autowired
    private BanterService banterService;

    @PostMapping("/message")
    public ResponseEntity<BanterMessage> sendMessage(@RequestHeader("Authorization") String token,
                                                    @RequestBody Map<String, String> request) {
        String userId = extractUserIdFromToken(token);
        BanterMessage response = banterService.sendMessage(userId, request.get("message"), request.get("conversationId"));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<BanterMessage>> getConversation(@RequestHeader("Authorization") String token,
                                                              @PathVariable String conversationId) {
        String userId = extractUserIdFromToken(token);
        List<BanterMessage> messages = banterService.getConversation(userId, conversationId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<String>> getUserConversations(@RequestHeader("Authorization") String token) {
        String userId = extractUserIdFromToken(token);
        List<String> conversations = banterService.getUserConversations(userId);
        return ResponseEntity.ok(conversations);
    }

    private String extractUserIdFromToken(String token) {
        return "user123"; // Mock implementation
    }
}