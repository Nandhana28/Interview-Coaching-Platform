package com.interviewcoaching.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MockTestConfig {

    @Bean
    public Map<String, String> testTypes() {
        Map<String, String> testTypes = new HashMap<>();
        testTypes.put("technical", "Technical Interview");
        testTypes.put("behavioral", "Behavioral Interview");
        testTypes.put("system_design", "System Design");
        testTypes.put("hr", "HR Interview");
        return testTypes;
    }
}