package com.thirdparty.controller;

import com.thirdparty.service.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thirdparty")
public class ThirdPartyController {

    @Autowired
    private ThirdPartyService thirdPartyService;

    @RequestMapping("/voucher")
    public ResponseEntity<String> getVoucherCode() throws Exception {
        String newVoucher = thirdPartyService.getVoucherCode();
        if (newVoucher.isEmpty()){
            System.out.println("Can not generate the voucher code");
            return new ResponseEntity<>(newVoucher, HttpStatus.GATEWAY_TIMEOUT);
        }
        return new ResponseEntity<>(newVoucher, HttpStatus.CREATED);
    }
}
