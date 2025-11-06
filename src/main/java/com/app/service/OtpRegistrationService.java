package com.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.entity.OTPRegister;
import com.app.repository.OTPRegisterRepository;

@Service
public class OtpRegistrationService {

    @Autowired
    private OTPRegisterRepository otpRepo;

    @Autowired
    private JavaMailSender mailSender;

    // Generate 4-digit OTP
    private String generateOTP() {
        return String.format("%04d", new Random().nextInt(10000));
    }

    // Send OTP via email
    public Map<String, Object> sendOtpEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        String otp = generateOTP();

        OTPRegister otpRecord = otpRepo.findByEmail(email)
                .orElse(new OTPRegister());

        otpRecord.setEmail(email);       // ✅ critical fix
        otpRecord.setOtp(otp);
        otpRecord.setStatus("PENDING");

        otpRepo.save(otpRecord);         // ✅ now safe to save

        // Send OTP email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Registration OTP");
        message.setText("Dear User,\n\nYour OTP for registration is: " + otp + "\n\n- CareConnect Team");
        mailSender.send(message);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "OTP sent successfully to " + email);
        response.put("email", email);
        return response;
    }

    // Verify OTP
    public boolean verifyOtp(String email, String enteredOtp) {
        Optional<OTPRegister> record = otpRepo.findByEmail(email);
        if (record.isPresent() && record.get().getOtp().equals(enteredOtp)) {
            OTPRegister otpRecord = record.get();
            otpRecord.setStatus("VERIFIED");
            otpRepo.save(otpRecord);
            return true;
        }
        return false;
    }
}