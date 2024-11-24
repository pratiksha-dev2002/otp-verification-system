package com.jsp.OTP_Verification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.OTP_Verification.entity.OTP_Verification;

@Repository
public interface OTPRepository extends JpaRepository<OTP_Verification, String> {

}
