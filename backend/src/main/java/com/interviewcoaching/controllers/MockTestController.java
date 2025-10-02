package com.interviewcoaching.controllers;

import com.interviewcoaching.services.MockTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mock-tests")
public class MockTestController {

    @Autowired
    private MockTestService mockTestService;
}