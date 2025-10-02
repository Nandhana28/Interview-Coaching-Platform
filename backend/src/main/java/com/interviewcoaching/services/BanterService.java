package com.interviewcoaching.services;

import com.interviewcoaching.models.BanterMessage;
import com.interviewcoaching.repositories.BanterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BanterService {

    @Autowired
    private BanterRepository banterRepository;

    public BanterMessage sendMessage(String userId, String message, String conversationId) {
        // Save user message
        BanterMessage userMessage = createMessage(userId, message, "USER", 
            conversationId != null ? conversationId : generateConversationId());
        banterRepository.save(userMessage);

        // Generate and save AI response
        BanterMessage aiResponse = generateAIResponse(userId, message, userMessage.getConversationId());
        return banterRepository.save(aiResponse);
    }

    public List<BanterMessage> getConversation(String userId, String conversationId) {
        List<BanterMessage> messages = banterRepository.findByConversationIdOrderByTimestamp(conversationId);
        
        // Verify user has access to this conversation
        if (!messages.isEmpty() && !messages.get(0).getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to conversation");
        }
        
        return messages;
    }

    public List<String> getUserConversations(String userId) {
        List<BanterMessage> allMessages = banterRepository.findByUserIdOrderByTimestampDesc(userId);
        
        // Extract unique conversation IDs
        return allMessages.stream()
                .map(BanterMessage::getConversationId)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<BanterMessage> getRecentConversations(String userId, int limit) {
        List<String> conversationIds = getUserConversations(userId);
        
        return conversationIds.stream()
                .limit(limit)
                .map(convId -> getLastMessageForConversation(convId))
                .filter(msg -> msg != null)
                .collect(Collectors.toList());
    }

    public boolean deleteConversation(String userId, String conversationId) {
        List<BanterMessage> messages = banterRepository.findByConversationIdOrderByTimestamp(conversationId);
        
        if (!messages.isEmpty() && !messages.get(0).getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this conversation");
        }
        
        //banterRepository.deleteByConversationId(conversationId);
        return true;
    }

    public Map<String, Object> getConversationStats(String userId) {
        List<BanterMessage> allMessages = banterRepository.findByUserIdOrderByTimestampDesc(userId);
        List<String> conversations = getUserConversations(userId);
        
        long totalMessages = allMessages.size();
        long userMessages = allMessages.stream()
                .filter(msg -> "USER".equals(msg.getType()))
                .count();
        long aiMessages = totalMessages - userMessages;
        
        // Calculate average response time (mock implementation)
        double avgResponseTime = calculateAverageResponseTime(allMessages);
        
        var stats = new java.util.HashMap<String, Object>();
        stats.put("totalConversations", conversations.size());
        stats.put("totalMessages", totalMessages);
        stats.put("userMessages", userMessages);
        stats.put("aiMessages", aiMessages);
        stats.put("avgResponseTime", avgResponseTime);
        stats.put("mostActiveDay", getMostActiveDay(allMessages));
        
        return stats;
    }

    private BanterMessage createMessage(String userId, String message, String type, String conversationId) {
        BanterMessage banterMessage = new BanterMessage();
        banterMessage.setId(UUID.randomUUID().toString());
        banterMessage.setUserId(userId);
        banterMessage.setMessage(message);
        banterMessage.setType(type);
        banterMessage.setTimestamp(new Date());
        banterMessage.setConversationId(conversationId);
        return banterMessage;
    }

    private BanterMessage generateAIResponse(String userId, String userMessage, String conversationId) {
        // Enhanced AI response generation
        String response = generateContextualResponse(userMessage);
        
        // Simulate typing delay
        simulateTypingDelay(response.length());
        
        return createMessage(userId, response, "AI", conversationId);
    }

    private String generateContextualResponse(String userMessage) {
        String lowerMessage = userMessage.toLowerCase();
        
        if (lowerMessage.contains("hello") || lowerMessage.contains("hi") || lowerMessage.contains("hey")) {
            return "Hello! I'm here to help you with interview preparation. What would you like to practice today?";
        } else if (lowerMessage.contains("technical") || lowerMessage.contains("coding")) {
            return "For technical interviews, I recommend practicing data structures and algorithms. Would you like me to generate some practice questions?";
        } else if (lowerMessage.contains("behavioral") || lowerMessage.contains("experience")) {
            return "Behavioral questions are great to practice! Remember to use the STAR method. Want to try a mock behavioral interview?";
        } else if (lowerMessage.contains("help") || lowerMessage.contains("advice")) {
            return "I can help you with: technical practice, behavioral questions, company research, and mock interviews. What specific area interests you?";
        } else {
            return "That's an interesting point! " + userMessage + " - This is a great topic to discuss. " +
                   "Would you like me to help you prepare a structured response for this?";
        }
    }

    private void simulateTypingDelay(int messageLength) {
        try {
            int delay = Math.min(2000, messageLength * 10); // Max 2 seconds
            Thread.sleep(500 + delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String generateConversationId() {
        return "conv_" + UUID.randomUUID().toString();
    }

    private BanterMessage getLastMessageForConversation(String conversationId) {
        List<BanterMessage> messages = banterRepository.findByConversationIdOrderByTimestamp(conversationId);
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }

    private double calculateAverageResponseTime(List<BanterMessage> messages) {
        // Mock implementation - in real app, calculate actual response times
        return 2.5; // seconds
    }

    private String getMostActiveDay(List<BanterMessage> messages) {
        // Mock implementation
        return "Monday";
    }
}