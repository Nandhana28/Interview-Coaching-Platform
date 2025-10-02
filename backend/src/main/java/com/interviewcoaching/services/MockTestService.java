package com.interviewcoaching.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class MockTestService {

    @Autowired
    private Map<String, String> TEST_TYPES;
}