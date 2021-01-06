package com.service.secure.service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.service.secure.dto.GenerateOTP;
import com.service.secure.dto.SecureRequest;
import com.service.secure.entity.OtpCode;
import com.service.secure.repository.OtpCodeRepository;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHex;

@Service
public class SecureServiceImpl implements SecureService{

    @Autowired
    OtpCodeRepository otpCodeRepository;

    @Autowired
    Environment env;

    @Override
    public int generateOTP(GenerateOTP generateOTP) throws NoSuchAlgorithmException, InvalidKeyException {

        TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();

        KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
        keyGenerator.init(256);
        Key key = keyGenerator.generateKey();

        final Instant now = Instant.now();
        char[] keys  = encodeHex(key.getEncoded());
        String data = String.valueOf(keys);

        OtpCode oldOtpCode = otpCodeRepository.findOtpCodeByPhoneNumber(generateOTP.getPhoneNumber());
        if (oldOtpCode != null){
            oldOtpCode.setSecretKey(data);
            oldOtpCode.setModifiedDate(now);
            oldOtpCode.setUsed(false);
            otpCodeRepository.save(oldOtpCode);
        } else {
            OtpCode newOtpCode = new OtpCode(generateOTP.getPhoneNumber(), data, now, now);
            otpCodeRepository.save(newOtpCode);
        }

        return totp.generateOneTimePassword(key, now);
    }

    @Override
    public boolean isSecure(SecureRequest secureRequest) throws NoSuchAlgorithmException, InvalidKeyException {
        TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
        int expiredTime = Integer.valueOf(env.getProperty("otp.code.expired"));
        OtpCode otpCode = otpCodeRepository.findOtpCodeByPhoneNumber(secureRequest.getPhoneNumber());
        if (otpCode == null || otpCode.isUsed() ||Duration.between(otpCode.getModifiedDate(), Instant.now()).toMinutes() >= expiredTime){
            return false;
        }

        String keys = otpCode.getSecretKey();
        Instant timeStamp = otpCode.getModifiedDate();

        byte[] encoded = new byte[0];
        try {
            encoded = decodeHex(keys.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        Key key = new SecretKeySpec(encoded, totp.getAlgorithm());
        Integer otpCodeDb = totp.generateOneTimePassword(key, timeStamp);

        boolean isSecure = otpCodeDb.equals(secureRequest.getOtpCode());
        if (isSecure){
            otpCode.setUsed(true);
            otpCodeRepository.save(otpCode);
        }

        return isSecure;
    }
}
