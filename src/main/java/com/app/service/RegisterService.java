package com.app.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserDAO;
import com.app.entity.User;
import com.app.repository.RegisterRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService {

   @Autowired UserDAO userDAO;
   @Autowired RegisterRepository registerRepository;

    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, LocalDateTime> otpGenerationTimes = new HashMap<>();

    private static final int OTP_EXPIRATION_SECONDS = 60;

    public boolean verifyOTP(String phoneNumber, String otpToVerify) {
        String storedOtp = otpStorage.get(phoneNumber);
        if (storedOtp != null && storedOtp.equals(otpToVerify)) {
            LocalDateTime generationTime = otpGenerationTimes.get(phoneNumber);
            LocalDateTime currentTime = LocalDateTime.now();
            long secondsElapsed = generationTime.until(currentTime, java.time.temporal.ChronoUnit.SECONDS);
            otpStorage.remove(phoneNumber);
            otpGenerationTimes.remove(phoneNumber);
            return secondsElapsed <= OTP_EXPIRATION_SECONDS;
        }
        return false;
    }

    public String otpcreate(int otpLength) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public void storeOtp(String phoneNumber, String otp) {
        otpStorage.put(phoneNumber, otp);
        otpGenerationTimes.put(phoneNumber, LocalDateTime.now());
    }

    public LocalDateTime getOTPGenerationTime(String mobileNo) {
        return otpGenerationTimes.get(mobileNo);
    }

    public User findByUserPhone(String phoneNo, UUID id) {
        return userDAO.findByUserPhone(phoneNo, id);
    }

    public User findByUserEmail(String email, UUID id) {
        return userDAO.findByUserEmail(email, id);
    }

    public Optional<User> findById(UUID id) {
        return registerRepository.findById(id);
    }

    public void update(User userObj) {
        registerRepository.saveAndFlush(userObj);
    }
}