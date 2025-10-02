package com.interviewcoaching.services;

import com.interviewcoaching.models.AIQuery;
import com.interviewcoaching.repositories.AIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AIService {

    @Autowired
    private AIRepository aiRepository;

    public AIQuery getAIResponse(String userId, String question, String category) {
        // Simulate AI processing delay
        simulateProcessingDelay();
        
        String response = generateAIResponse(question, category);
        
        AIQuery aiQuery = new AIQuery();
        aiQuery.setId(UUID.randomUUID().toString());
        aiQuery.setUserId(userId);
        aiQuery.setQuestion(question);
        aiQuery.setResponse(response);
        aiQuery.setCategory(category != null ? category : "General");
        aiQuery.setCreatedAt(new Date());
        
        return aiRepository.save(aiQuery);
    }

    public List<AIQuery> getUserQueries(String userId) {
        return aiRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<AIQuery> getUserQueriesByCategory(String userId, String category) {
        return aiRepository.findByUserIdAndCategory(userId, category);
    }

    public AIQuery getQueryById(String queryId) {
        return aiRepository.findById(queryId)
                .orElseThrow(() -> new RuntimeException("Query not found"));
    }

    public boolean deleteQuery(String queryId, String userId) {
        AIQuery query = aiRepository.findById(queryId)
                .orElseThrow(() -> new RuntimeException("Query not found"));
        
        if (!query.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to delete this query");
        }
        
        aiRepository.deleteById(queryId);
        return true;
    }

    public Map<String, Long> getQueryStats(String userId) {
        List<AIQuery> allQueries = getUserQueries(userId);
        long totalQueries = allQueries.size();
        long todayQueries = allQueries.stream()
                .filter(query -> isToday(query.getCreatedAt()))
                .count();
        
        var stats = new java.util.HashMap<String, Long>();
        stats.put("totalQueries", totalQueries);
        stats.put("todayQueries", todayQueries);
        stats.put("thisWeekQueries", allQueries.stream()
                .filter(query -> isThisWeek(query.getCreatedAt()))
                .count());
        
        // Category distribution
        allQueries.stream()
                .collect(java.util.stream.Collectors.groupingBy(AIQuery::getCategory, java.util.stream.Collectors.counting()))
                .forEach(stats::put);
        
        return stats;
    }

    private String generateAIResponse(String question, String category) {
        // Enhanced mock AI response based on category
        String baseResponse = "Based on your question about \"" + question + "\"";
        
        switch (category != null ? category.toLowerCase() : "general") {
            case "technical":
                return baseResponse + ", I recommend reviewing core concepts and practicing coding problems. " +
                       "Focus on data structures, algorithms, and system design principles.";
            
            case "behavioral":
                return baseResponse + ", remember to use the STAR method (Situation, Task, Action, Result). " +
                       "Prepare specific examples that demonstrate your skills and achievements.";
            
            case "aptitude":
                return baseResponse + ", practice logical reasoning and quantitative problems. " +
                       "Work on improving your speed and accuracy with timed practice sessions.";
            
            case "company-specific":
                return baseResponse + ", research the company's culture and recent projects. " +
                       "Prepare questions that show your interest in their specific work.";
            
            default:
                return baseResponse + ", I recommend practicing common interview questions and focusing on your communication skills. " +
                       "Make sure to research the company and role thoroughly before your interview.";
        }
    }

    private void simulateProcessingDelay() {
        try {
            Thread.sleep(1000 + (long)(Math.random() * 2000)); // 1-3 second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isToday(Date date) {
        Date today = new Date();
        return date != null && 
               date.getYear() == today.getYear() &&
               date.getMonth() == today.getMonth() &&
               date.getDate() == today.getDate();
    }

    private boolean isThisWeek(Date date) {
        if (date == null) return false;
        
        Date today = new Date();
        long diff = today.getTime() - date.getTime();
        return diff <= 7 * 24 * 60 * 60 * 1000; // Within 7 days
    }
}