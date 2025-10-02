package com.interviewcoaching.controllers;

import com.interviewcoaching.models.User;
import com.interviewcoaching.services.AuthService;
import com.interviewcoaching.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // Register user
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User createdUser = authService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok(createdUser);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String token = authService.loginUser(email, password);
        return ResponseEntity.ok(token);
    }

    // Request OTP (email)
    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) return ResponseEntity.badRequest().body("Email not registered");

        String otp = userService.generateOtp(user);
        return ResponseEntity.ok("OTP sent: " + otp); // In real projects, send via email/SMS
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        User user = userService.getUserByEmail(email);
        if (user == null) return ResponseEntity.badRequest().body("Email not registered");

        boolean verified = userService.verifyOtp(user, otp);
        if (verified) return ResponseEntity.ok("OTP verified successfully");
        return ResponseEntity.badRequest().body("Invalid or expired OTP");
    }
}
