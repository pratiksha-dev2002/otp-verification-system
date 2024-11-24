package com.jsp.OTP_Verification.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.OTP_Verification.entity.OTP_Verification;
import com.jsp.OTP_Verification.repository.OTPRepository;

@Service
public class OTPService {
	
	    @Autowired
	    private OTPRepository otpRepository;

	    public String generateOtp() {
	        int otp = ThreadLocalRandom.current().nextInt(100000, 999999);
	        return String.valueOf(otp);
	    }

	    public String hashOtp(String otp) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hashBytes = md.digest(otp.getBytes());
	        return Base64.getEncoder().encodeToString(hashBytes);
	    }

	    public void sendOtp(String userId) throws NoSuchAlgorithmException {
	        String otp = generateOtp();
	        String hashedOtp = hashOtp(otp);

	        OTP_Verification otpRecord = new OTP_Verification();
	        otpRecord.setUserId(userId);
	        otpRecord.setHashedOtp(hashedOtp);
	        otpRecord.setExpiryTime(LocalDateTime.now().plusMinutes(5));
	        otpRecord.setResendAttempts(0);

	        otpRepository.save(otpRecord);

	        // Mock send OTP
	        System.out.println("OTP sent to user: " + otp);
	    }

	    public boolean verifyOtp(String userId, String inputOtp) throws NoSuchAlgorithmException {
	        OTP_Verification otpRecord = otpRepository.findById(userId).orElse(null);
	        if (otpRecord == null || LocalDateTime.now().isAfter(otpRecord.getExpiryTime())) {
	            return false;
	        }

	        String hashedInput = hashOtp(inputOtp);
	        return hashedInput.equals(otpRecord.getHashedOtp());
	    }

	    public void resendOtp(String userId) throws NoSuchAlgorithmException {
	        OTP_Verification otpRecord = otpRepository.findById(userId).orElse(null);
	        if (otpRecord != null) {
	            otpRecord.setResendAttempts(otpRecord.getResendAttempts() + 1);
	            otpRecord.setExpiryTime(LocalDateTime.now().plusMinutes(5));
	            sendOtp(userId);
	        }
	    }
	}

