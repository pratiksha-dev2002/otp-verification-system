package com.jsp.OTP_Verification.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTP_Verification {
	
	 	@Id
	    private String userId;
	    private String hashedOtp;
	    private LocalDateTime expiryTime;
	    private int resendAttempts;

}
