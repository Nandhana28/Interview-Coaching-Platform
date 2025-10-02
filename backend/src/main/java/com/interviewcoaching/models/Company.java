package com.interviewcoaching.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "companies")
public class Company {
    @Id
    private String id;
    private String name;
    private String industry;
    private String description;
    private List<String> interviewQuestions;
    private List<String> preparationTips;
    private List<String> technologies;
    private String difficulty;

    // Constructors, Getters and Setters
    public Company() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getInterviewQuestions() { return interviewQuestions; }
    public void setInterviewQuestions(List<String> interviewQuestions) { this.interviewQuestions = interviewQuestions; }

    public List<String> getPreparationTips() { return preparationTips; }
    public void setPreparationTips(List<String> preparationTips) { this.preparationTips = preparationTips; }

    public List<String> getTechnologies() { return technologies; }
    public void setTechnologies(List<String> technologies) { this.technologies = technologies; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}
