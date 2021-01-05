package com.service.secure.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SecureService {
    int generateOTP(String phoneNumber) throws NoSuchAlgorithmException, InvalidKeyException;
}
