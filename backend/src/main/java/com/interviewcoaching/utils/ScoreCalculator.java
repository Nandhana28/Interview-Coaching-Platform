package com.interviewcoaching.utils;

import org.springframework.stereotype.Component;

@Component
public class ScoreCalculator {

    public int calculateAptitudeScore(int correctAnswers, int totalQuestions) {
        return (int) ((double) correctAnswers / totalQuestions * 100);
    }

    public int calculateTechnicalScore(int correctAnswers, int totalQuestions, double difficultyMultiplier) {
        return (int) ((double) correctAnswers / totalQuestions * 100 * difficultyMultiplier);
    }

    public String getPerformanceFeedback(int score) {
        if (score >= 90) return "Excellent! You're well prepared.";
        if (score >= 75) return "Good job! Some areas need improvement.";
        if (score >= 60) return "Fair. Consider more practice.";
        return "Needs significant improvement. Focus on fundamentals.";
    }
}