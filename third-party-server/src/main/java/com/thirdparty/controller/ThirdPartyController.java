package com.thirdparty.controller;

import com.thirdparty.service.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThirdPartyController {

    @Autowired
    private ThirdPartyService thirdPartyService;

    @RequestMapping("/evoucher")
    public ResponseEntity<String> getVoucherCode() throws Exception {
        String newVoucher = thirdPartyService.getVoucherCode();
        if (newVoucher.isEmpty()){
            System.out.println("Can not generate the voucher code");
            return new ResponseEntity<>(newVoucher, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(newVoucher, HttpStatus.OK);
    }
}
