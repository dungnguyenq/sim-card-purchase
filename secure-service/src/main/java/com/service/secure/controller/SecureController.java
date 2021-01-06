package com.service.secure.controller;

import com.google.common.base.Strings;
import com.service.secure.dto.GenerateOTP;
import com.service.secure.dto.SecureRequest;
import com.service.secure.dto.SmsDto;
import com.service.secure.service.SecureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController("/")
public class SecureController {

    final static Logger logger = LogManager.getLogger(SecureController.class);

    @Autowired
    SecureService secureService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    Environment env;

    @PostMapping("/generate")
    public void generateOTP(@RequestBody GenerateOTP generateOTP){
        int data = 0;
        try{
            data = secureService.generateOTP(generateOTP);
            SmsDto smsDto = new SmsDto(generateOTP.getPhoneNumber(), "YOUR OTP: " + data);
            String smsId = restTemplate.postForObject(env.getProperty("sms.service.endpoint.send"), smsDto, String.class);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> isSecure(@RequestBody SecureRequest secureRequest){
        Boolean data = false;
        try{
            data = secureService.isSecure(secureRequest);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
