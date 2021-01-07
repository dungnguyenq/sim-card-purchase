package com.service.voucher.controller;

import com.service.voucher.dto.GenerateRequest;
import com.service.voucher.dto.VoucherDto;
import com.service.voucher.service.VoucherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    final static Logger logger = LogManager.getLogger(VoucherController.class);

    @PostMapping("/generate")
    public ResponseEntity<VoucherDto> generateVoucher(@RequestBody GenerateRequest generateRequest){
        VoucherDto voucherDto = new VoucherDto();
        try{
            voucherDto = voucherService.generateVoucher(generateRequest.getPhoneNumber());
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseEntity(voucherDto, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(voucherDto, HttpStatus.OK);
    }

    @GetMapping("/vouchers")
    public List<VoucherDto> getVouchers(@RequestParam(value = "phonenumber", required = true) String phoneNumber){
        List<VoucherDto> results = new ArrayList<>();
        try{
            results = voucherService.getVouchers(phoneNumber);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return results;
    }
}
