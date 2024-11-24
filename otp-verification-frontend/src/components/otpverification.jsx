import React, { useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const OtpVerification = () => {
  const [userId, setUserId] = useState("");
  const [otp, setOtp] = useState("");
  const [isOtpSent, setIsOtpSent] = useState(false);

  const handleSendOtp = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/otp/send", null, {
        params: { userId },
      });
      toast.success(response.data);
      setIsOtpSent(true);
    } catch (error) {
      toast.error(
        error.response?.data || "Failed to send OTP. Please try again."
      );
    }
  };

  const handleVerifyOtp = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/otp/verify", null, {
        params: { userId, otp },
      });
      toast.success(response.data);
    } catch (error) {
      toast.error(
        error.response?.data || "OTP verification failed. Please try again."
      );
    }
  };

  const handleResendOtp = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/otp/resend", null, {
        params: { userId },
      });
      toast.success(response.data);
    } catch (error) {
      toast.error(
        error.response?.data || "Failed to resend OTP. Please try again."
      );
    }
  };

  return (
    <div className="otp-verification">
      <h2>OTP Verification</h2>
      <div>
        <label>User ID (Phone/Email):</label>
        <input
          type="text"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          placeholder="Enter phone or email"
        />
      </div>
      {isOtpSent && (
        <div>
          <label>Enter OTP:</label>
          <input
            type="text"
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
            placeholder="Enter 6-digit OTP"
          />
        </div>
      )}
      <div>
        {!isOtpSent ? (
          <button onClick={handleSendOtp}>Send OTP</button>
        ) : (
          <>
            <button onClick={handleVerifyOtp}>Verify OTP</button>
            <button onClick={handleResendOtp}>Resend OTP</button>
          </>
        )}
      </div>
      <ToastContainer />
    </div>
  );
};

export default OtpVerification;
