package com.service.secure.controller;

import com.service.secure.dto.GenerateOTP;
import com.service.secure.dto.SecureRequest;
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
    public ResponseEntity<Integer> generateOTP(@RequestBody GenerateOTP generateOTP){
        int data = 0;
        try{
            data = secureService.generateOTP(generateOTP);
        } catch (Exception ex){
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/secure")
    public ResponseEntity<Boolean> isSecure(@RequestBody SecureRequest secureRequest){
        Boolean data = false;
        try{
            data = secureService.isSecure(secureRequest);
        } catch (Exception ex){
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
