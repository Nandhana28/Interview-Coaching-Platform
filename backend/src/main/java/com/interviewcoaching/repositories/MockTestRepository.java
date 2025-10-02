package com.interviewcoaching.repositories;

import com.interviewcoaching.models.MockTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MockTestRepository extends MongoRepository<MockTest, String> {
    List<MockTest> findByUserIdOrderByCompletedAtDesc(String userId);
    List<MockTest> findByUserIdAndTestType(String userId, String testType);
    List<MockTest> findByUserIdAndStatus(String userId, String status);
}
