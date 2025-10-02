package com.interviewcoaching.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "mock_tests")
public class MockTest {
    @Id
    private String id;
    private String userId;
    private String testType; // "APTITUDE", "TECHNICAL", "BEHAVIORAL"
    private List<String> questionIds;
    private Map<String, Integer> userAnswers; // questionId -> selectedOptionIndex
    private int score;
    private int totalQuestions;
    private Date startedAt;
    private Date completedAt;
    private String status; // "IN_PROGRESS", "COMPLETED"

    // Constructors, Getters and Setters
    public MockTest() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }

    public List<String> getQuestionIds() { return questionIds; }
    public void setQuestionIds(List<String> questionIds) { this.questionIds = questionIds; }

    public Map<String, Integer> getUserAnswers() { return userAnswers; }
    public void setUserAnswers(Map<String, Integer> userAnswers) { this.userAnswers = userAnswers; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public Date getStartedAt() { return startedAt; }
    public void setStartedAt(Date startedAt) { this.startedAt = startedAt; }

    public Date getCompletedAt() { return completedAt; }
    public void setCompletedAt(Date completedAt) { this.completedAt = completedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
