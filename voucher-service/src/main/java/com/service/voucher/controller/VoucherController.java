package com.service.voucher.controller;

import com.service.voucher.dto.GenerateRequest;
import com.service.voucher.dto.VoucherDto;
import com.service.voucher.enumeration.Constant;
import com.service.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @PostMapping("/generate")
    public ResponseEntity<VoucherDto> generateVoucher(@RequestBody GenerateRequest generateRequest){
        VoucherDto voucherDto = new VoucherDto();
        voucherDto = voucherService.generateVoucher(generateRequest.getPhoneNumber());

        return new ResponseEntity(voucherDto, HttpStatus.OK);
    }


}
