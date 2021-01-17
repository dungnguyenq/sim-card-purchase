package com.service.secure.controller;

import com.google.common.base.Strings;
import com.service.secure.dto.PhoneNumberDto;
import com.service.secure.dto.OtpCodeDto;
import com.service.secure.dto.SmsDto;
import com.service.secure.service.OtpCodeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class OtpCodeController {

    final static Logger logger = LogManager.getLogger(OtpCodeController.class);

    @Autowired
    OtpCodeService otpCodeService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Environment env;

    @PostMapping("/otp")
    public ResponseEntity getOTP(@RequestBody PhoneNumberDto phoneNumberDto){
        int data = 0;
        try{
            data = otpCodeService.generateOTP(phoneNumberDto);
            SmsDto smsDto = new SmsDto(phoneNumberDto.getPhoneNumber(), "YOUR OTP: " + data);
            String smsId = restTemplate.postForObject(env.getProperty("sms.service.endpoint.send"), smsDto, String.class);
            if (Strings.isNullOrEmpty(smsId)){
                return new ResponseEntity(HttpStatus.BAD_GATEWAY);
            }
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/otp/{phone-number}")
    public ResponseEntity<Boolean> checkOTP(@PathVariable("phone-number") String phoneNumber, @RequestBody OtpCodeDto otpCodeDto){
        Boolean data = false;
        try{
            data = otpCodeService.isSecure(phoneNumber, otpCodeDto);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
