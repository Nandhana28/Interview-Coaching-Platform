package com.interviewcoaching.services;

import com.interviewcoaching.models.User;
import com.interviewcoaching.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create user
    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRoles(List.of("USER"));
        user.setVerified(false);
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by email
    public User getUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.orElse(null);
    }

    // Update user
    public User updateUser(String id, User updatedUser) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return null;

        User user = userOpt.get();
        user.setUsername(updatedUser.getUsername() != null ? updatedUser.getUsername() : user.getUsername());
        user.setFirstName(updatedUser.getFirstName() != null ? updatedUser.getFirstName() : user.getFirstName());
        user.setLastName(updatedUser.getLastName() != null ? updatedUser.getLastName() : user.getLastName());
        user.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : user.getEmail());
        user.setPhoneNumber(updatedUser.getPhoneNumber() != null ? updatedUser.getPhoneNumber() : user.getPhoneNumber());
        user.setPassword(updatedUser.getPassword() != null ? updatedUser.getPassword() : user.getPassword());
        user.setSignInUsing(updatedUser.getSignInUsing() != null ? updatedUser.getSignInUsing() : user.getSignInUsing());
        user.setSecondaryEmail(updatedUser.getSecondaryEmail() != null ? updatedUser.getSecondaryEmail() : user.getSecondaryEmail());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Delete user
    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Generate 6-digit OTP
    public String generateOtp(User user) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        user.setOtp(String.valueOf(otp));
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        user.setLastOtpRequestedAt(LocalDateTime.now());
        userRepository.save(user);
        return user.getOtp();
    }

    // Verify OTP
    public boolean verifyOtp(User user, String otp) {
        if (user.getOtp() != null &&
            user.getOtp().equals(otp) &&
            user.getOtpExpiry() != null &&
            user.getOtpExpiry().isAfter(LocalDateTime.now())) {
            user.setVerified(true);
            user.setOtp(null);
            user.setOtpExpiry(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
