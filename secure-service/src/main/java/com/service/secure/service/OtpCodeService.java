package com.service.secure.service;

import com.service.secure.dto.PhoneNumberDto;
import com.service.secure.dto.OtpCodeDto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface OtpCodeService {
    int generateOTP(PhoneNumberDto phoneNumberDto) throws NoSuchAlgorithmException, InvalidKeyException;
    boolean isSecure(String phoneNumber, OtpCodeDto otpCodeDto) throws NoSuchAlgorithmException, InvalidKeyException;
}
