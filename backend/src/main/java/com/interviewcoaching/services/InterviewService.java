package com.interviewcoaching.services;

import com.interviewcoaching.models.InterviewQuestion;
import com.interviewcoaching.models.Company;
import com.interviewcoaching.repositories.InterviewRepository;
import com.interviewcoaching.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private final Map<String, String> QUESTION_CATEGORIES = Map.of(
        "technical", "Technical Questions",
        "behavioral", "Behavioral Questions",
        "situational", "Situational Questions",
        "leadership", "Leadership Questions",
        "cultural", "Cultural Fit Questions"
    );

    private final Map<String, String> DIFFICULTY_LEVELS = Map.of(
        "easy", "Easy",
        "medium", "Medium", 
        "hard", "Hard"
    );

    public List<InterviewQuestion> getAllQuestions() {
        return interviewRepository.findAll();
    }

    public List<InterviewQuestion> getQuestionsByCategory(String category) {
        return interviewRepository.findByCategory(category);
    }

    public List<InterviewQuestion> getQuestionsByDifficulty(String difficulty) {
        return interviewRepository.findByDifficulty(difficulty);
    }

    public List<InterviewQuestion> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
        return interviewRepository.findByCategoryAndDifficulty(category, difficulty);
    }

    public InterviewQuestion getRandomQuestion() {
        List<InterviewQuestion> allQuestions = interviewRepository.findAll();
        if (allQuestions.isEmpty()) {
            throw new RuntimeException("No questions available");
        }
        return allQuestions.get(new Random().nextInt(allQuestions.size()));
    }

    public List<InterviewQuestion> getPracticeSession(String category, String difficulty, int count) {
        List<InterviewQuestion> questions;
        
        if (category != null && difficulty != null) {
            questions = interviewRepository.findByCategoryAndDifficulty(category, difficulty);
        } else if (category != null) {
            questions = interviewRepository.findByCategory(category);
        } else if (difficulty != null) {
            questions = interviewRepository.findByDifficulty(difficulty);
        } else {
            questions = interviewRepository.findAll();
        }
        
        // Shuffle and limit
        Collections.shuffle(questions);
        return questions.stream()
                .limit(Math.min(count, questions.size()))
                .collect(Collectors.toList());
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getCompaniesByIndustry(String industry) {
        return companyRepository.findByIndustry(industry);
    }

    public Company getCompanyById(String companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public List<Company> searchCompanies(String query) {
        return companyRepository.findByNameContainingIgnoreCase(query);
    }

    public Map<String, Object> getInterviewStatistics() {
        List<InterviewQuestion> allQuestions = getAllQuestions();
        List<Company> allCompanies = getAllCompanies();
        
        Map<String, Long> questionsByCategory = allQuestions.stream()
                .collect(Collectors.groupingBy(InterviewQuestion::getCategory, Collectors.counting()));
        
        Map<String, Long> questionsByDifficulty = allQuestions.stream()
                .collect(Collectors.groupingBy(InterviewQuestion::getDifficulty, Collectors.counting()));
        
        Map<String, Long> companiesByIndustry = allCompanies.stream()
                .collect(Collectors.groupingBy(Company::getIndustry, Collectors.counting()));
        
        return Map.of(
            "totalQuestions", allQuestions.size(),
            "totalCompanies", allCompanies.size(),
            "questionsByCategory", questionsByCategory,
            "questionsByDifficulty", questionsByDifficulty,
            "companiesByIndustry", companiesByIndustry,
            "categories", QUESTION_CATEGORIES,
            "difficultyLevels", DIFFICULTY_LEVELS
        );
    }

    public InterviewQuestion addQuestion(InterviewQuestion question) {
        question.setId(UUID.randomUUID().toString());
        return interviewRepository.save(question);
    }

    public Company addCompany(Company company) {
        company.setId(UUID.randomUUID().toString());
        return companyRepository.save(company);
    }

    public List<String> getAvailableCategories() {
        return new ArrayList<>(QUESTION_CATEGORIES.keySet());
    }

    public List<String> getAvailableDifficulties() {
        return new ArrayList<>(DIFFICULTY_LEVELS.keySet());
    }

    public Map<String, List<InterviewQuestion>> getQuestionsByMultipleCategories(List<String> categories) {
        return categories.stream()
                .collect(Collectors.toMap(
                    category -> category,
                    category -> getQuestionsByCategory(category)
                ));
    }

    public List<InterviewQuestion> getQuestionsWithTips() {
        return interviewRepository.findAll().stream()
                .filter(question -> question.getTips() != null && !question.getTips().isEmpty())
                .collect(Collectors.toList());
    }
}