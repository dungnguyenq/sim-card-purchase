package com.service.secure.service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Service
public class SecureServiceImpl implements SecureService{
    @Override
    public int generateOTP(String phoneNumber) throws NoSuchAlgorithmException, InvalidKeyException {

        TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();

        KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());

        // Key length should match the length of the HMAC output (160 bits for SHA-1, 256 bits
        // for SHA-256, and 512 bits for SHA-512).
        keyGenerator.init(160);

        Key key = keyGenerator.generateKey();

        final Instant now = Instant.now();
        return totp.generateOneTimePassword(key, now);
    }
}
