package com.interviewcoaching.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "interview_questions")
public class InterviewQuestion {
    @Id
    private String id;
    private String question;
    private String answer;
    private String category;
    private String difficulty;
    private String tips;
    private String[] keywords;

    // Constructors, Getters and Setters
    public InterviewQuestion() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getTips() { return tips; }
    public void setTips(String tips) { this.tips = tips; }

    public String[] getKeywords() { return keywords; }
    public void setKeywords(String[] keywords) { this.keywords = keywords; }
}
