package com.interviewcoaching.repositories;

import com.interviewcoaching.models.AIQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AIRepository extends MongoRepository<AIQuery, String> {
    List<AIQuery> findByUserIdOrderByCreatedAtDesc(String userId);
    List<AIQuery> findByUserIdAndCategory(String userId, String category);
}
