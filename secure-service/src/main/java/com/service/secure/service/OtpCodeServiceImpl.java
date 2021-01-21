package com.service.secure.service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.service.secure.dto.PhoneNumberDto;
import com.service.secure.dto.OtpCodeDto;
import com.service.secure.entity.OtpCode;
import com.service.secure.repository.OtpCodeRepository;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class OtpCodeServiceImpl implements OtpCodeService {

    @Autowired
    OtpCodeRepository otpCodeRepository;

    @Value("${otp.code.expired}")
    private int expiredTimeInMinutes;

    @Override
    public int generateOTP(PhoneNumberDto phoneNumberDto) throws NoSuchAlgorithmException, InvalidKeyException {

        TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
        Key key = generateKey(totp.getAlgorithm());

        final Instant now = Instant.now();
        String secretKey = encodeHax(key);

        OtpCode otpCode = otpCodeRepository.findOtpCodeByPhoneNumber(phoneNumberDto.getPhoneNumber());
        if (otpCode == null){
            otpCode = new OtpCode();
            otpCode.setPhoneNumber(phoneNumberDto.getPhoneNumber());
            otpCode.setCreatedDate(now);
        }
        otpCode.setSecretKey(secretKey);
        otpCode.setUsed(false);
        otpCode.setModifiedDate(now);
        otpCodeRepository.save(otpCode);

        return totp.generateOneTimePassword(key, now);
    }

    @Override
    public boolean isSecure(String phoneNumber, OtpCodeDto otpCodeDto) throws NoSuchAlgorithmException, InvalidKeyException {
        TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
        OtpCode otpCode = otpCodeRepository.findOtpCodeByPhoneNumber(phoneNumber);

        if (otpCode == null || otpCode.isUsed() ||Duration.between(otpCode.getModifiedDate(), Instant.now()).toMinutes() >= expiredTimeInMinutes){
            return false;
        }

        String secretKey = otpCode.getSecretKey();
        Instant timeStamp = otpCode.getModifiedDate();

        Key key = new SecretKeySpec(decodeHax(secretKey), totp.getAlgorithm());
        Integer otpCodeDb = totp.generateOneTimePassword(key, timeStamp);

        boolean isSecure = otpCodeDb.equals(otpCodeDto.getOtpCode());
        if (isSecure){
            otpCode.setUsed(true);
            otpCodeRepository.save(otpCode);
        }

        return isSecure;
    }

    private Key generateKey(String algorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private String encodeHax(Key key){
        char[] keys  = encodeHex(key.getEncoded());
        return String.valueOf(keys);
    }

    private byte[] decodeHax(String secretKey){
        byte[] encoded = new byte[0];
        try {
            encoded = decodeHex(secretKey.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return encoded;
    }
}
