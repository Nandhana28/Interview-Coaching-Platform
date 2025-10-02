package com.interviewcoaching.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsOtpService {

    @Value("${twilio.sid}")
    private String accountSid;

    @Value("${twilio.token}")
    private String authToken;

    @Value("${twilio.from}")
    private String fromNumber;

    public String generateOtp() {
        int otp = 100000 + (int)(Math.random() * 900000);
        return String.valueOf(otp);
    }

    public void sendOtpSms(String toNumber, String otp) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber),
                "Your OTP is: " + otp
        ).create();
    }
}