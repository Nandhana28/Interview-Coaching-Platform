package com.interviewcoaching.repositories;

import com.interviewcoaching.models.InterviewQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface InterviewRepository extends MongoRepository<InterviewQuestion, String> {
    List<InterviewQuestion> findByCategory(String category);
    List<InterviewQuestion> findByDifficulty(String difficulty);
    List<InterviewQuestion> findByCategoryAndDifficulty(String category, String difficulty);
}
