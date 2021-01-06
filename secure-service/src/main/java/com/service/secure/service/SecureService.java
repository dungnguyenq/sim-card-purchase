package com.service.secure.service;

import com.service.secure.dto.GenerateOTP;
import com.service.secure.dto.SecureRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SecureService {
    int generateOTP(GenerateOTP generateOTP) throws NoSuchAlgorithmException, InvalidKeyException;
    boolean isSecure(SecureRequest secureRequest) throws NoSuchAlgorithmException, InvalidKeyException;
}
