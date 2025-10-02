package com.interviewcoaching.repositories;

import com.interviewcoaching.models.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {
    List<Company> findByIndustry(String industry);
    List<Company> findByNameContainingIgnoreCase(String name);
    List<Company> findByDifficulty(String difficulty);
}
