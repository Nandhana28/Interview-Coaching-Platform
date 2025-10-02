package com.interviewcoaching.config;

import com.interviewcoaching.models.User;
import com.interviewcoaching.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
public class dbConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@example.com";
        
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail(adminEmail);
            admin.setPhoneNumber("+911234567890");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setSignInUsing("Email");
            admin.setRoles(Collections.singletonList("ROLE_ADMIN"));
            admin.setVerified(true);
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            admin.setOtp(null);
            admin.setOtpExpiry(null);
            admin.setOtpAttempts(0);
            admin.setLastOtpRequestedAt(null);

            userRepository.save(admin);
            System.out.println("Default admin user created in MongoDB");
        }
    }
}