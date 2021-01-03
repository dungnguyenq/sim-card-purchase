package com.service.sms.controller;

import com.service.sms.dto.SmsDto;
import com.service.sms.service.TwilioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {

    final static Logger logger = LogManager.getLogger(SmsController.class);

    @Autowired
    TwilioService twilioService;

    // can not use in this app, will be use latter.
    @RequestMapping("/send")
    public ResponseEntity<String> sendSMS(@RequestBody SmsDto smsDto){
        String data = "";
        try {
            data = twilioService.sendSMS(smsDto);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
