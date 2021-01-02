package com.service.voucher.controller;

import com.service.voucher.dto.VoucherDto;
import com.service.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class VouchersController {

    @Autowired
    VoucherService voucherService;

    @GetMapping("/vouchers")
    public List<VoucherDto> getVouchers(@RequestParam(value = "phonenumber", required = true) String phoneNumber){
        List<VoucherDto> results = new ArrayList<>();
        results = voucherService.getVouchers(phoneNumber);
        return results;
    }
}
