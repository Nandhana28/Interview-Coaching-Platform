package com.interviewcoaching.controllers;

import com.interviewcoaching.models.InterviewQuestion;
import com.interviewcoaching.models.Company;
import com.interviewcoaching.services.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interview")
@CrossOrigin(origins = "http://localhost:3000")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping("/questions")
    public ResponseEntity<List<InterviewQuestion>> getQuestions(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty) {
        
        if (category != null && difficulty != null) {
            return ResponseEntity.ok(interviewService.getQuestionsByCategoryAndDifficulty(category, difficulty));
        } else if (category != null) {
            return ResponseEntity.ok(interviewService.getQuestionsByCategory(category));
        } else if (difficulty != null) {
            return ResponseEntity.ok(interviewService.getQuestionsByDifficulty(difficulty));
        } else {
            return ResponseEntity.ok(interviewService.getAllQuestions());
        }
    }

    @GetMapping("/questions/random")
    public ResponseEntity<InterviewQuestion> getRandomQuestion() {
        return ResponseEntity.ok(interviewService.getRandomQuestion());
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getCompanies(@RequestParam(required = false) String industry) {
        if (industry != null) {
            return ResponseEntity.ok(interviewService.getCompaniesByIndustry(industry));
        }
        return ResponseEntity.ok(interviewService.getAllCompanies());
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String id) {
        return ResponseEntity.ok(interviewService.getCompanyById(id));
    }

    @PostMapping("/practice/session")
    public ResponseEntity<Map<String, Object>> startPracticeSession(@RequestBody Map<String, Object> request) {
        String category = (String) request.get("category");
        String difficulty = (String) request.get("difficulty");
        int questionCount = (int) request.getOrDefault("questionCount", 5);
        
        List<InterviewQuestion> questions = interviewService.getPracticeSession(category, difficulty, questionCount);
        
        return ResponseEntity.ok(Map.of(
            "sessionId", java.util.UUID.randomUUID().toString(),
            "questions", questions,
            "totalQuestions", questions.size()
        ));
    }
}