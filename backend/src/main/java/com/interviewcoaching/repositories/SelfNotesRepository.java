package com.interviewcoaching.repositories;

import com.interviewcoaching.models.SelfNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SelfNotesRepository extends MongoRepository<SelfNote, String> {
    List<SelfNote> findByUserIdOrderByUpdatedAtDesc(String userId);
    List<SelfNote> findByUserIdAndCategory(String userId, String category);
    List<SelfNote> findByUserIdAndTagsContaining(String userId, String tag);
}
