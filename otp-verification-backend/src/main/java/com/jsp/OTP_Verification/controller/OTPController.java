package com.jsp.OTP_Verification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.OTP_Verification.service.OTPService;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin(origins="http://127.0.0.1:5173/")
public class OTPController {
	
	@Autowired
    private OTPService otpService;

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String userId) {
        try {
            otpService.sendOtp(userId);
            return ResponseEntity.ok("OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send OTP");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String userId, @RequestParam String otp) {
        try {
            boolean isValid = otpService.verifyOtp(userId, otp);
            if (isValid) {
                return ResponseEntity.ok("OTP verified successfully");
            } else {
                return ResponseEntity.status(400).body("Invalid or expired OTP");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error verifying OTP");
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendOtp(@RequestParam String userId) {
        try {
            otpService.resendOtp(userId);
            return ResponseEntity.ok("OTP resent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to resend OTP");
        }
    }

}
