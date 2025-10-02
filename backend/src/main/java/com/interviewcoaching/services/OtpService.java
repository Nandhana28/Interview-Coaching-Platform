package com.interviewcoaching.services;

import com.interviewcoaching.models.User;
import com.interviewcoaching.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;

    public String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }

    public void generateAndSendOtp(String email, User user) {
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
        user.setOtpAttempts(0);
        user.setLastOtpRequestedAt(LocalDateTime.now());
        userRepository.save(user);

        System.out.println("OTP for " + email + " : " + otp);
    }
}