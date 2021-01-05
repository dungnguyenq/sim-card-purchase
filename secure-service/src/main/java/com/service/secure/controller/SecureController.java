package com.service.secure.controller;

import com.service.secure.dto.GenerateOTP;
import com.service.secure.service.SecureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class SecureController {

    @Autowired
    SecureService secureService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateOTP(@RequestBody GenerateOTP generateOTP){
        String data = "";
        try{
            data = Integer.toString(secureService.generateOTP(generateOTP.getPhoneNumber()));
        } catch (Exception ex){
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
