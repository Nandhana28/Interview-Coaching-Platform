package com.interviewcoaching.repositories;

import com.interviewcoaching.models.BanterMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BanterRepository extends MongoRepository<BanterMessage, String> {
    List<BanterMessage> findByUserIdOrderByTimestampDesc(String userId);
    List<BanterMessage> findByConversationIdOrderByTimestamp(String conversationId);
}
