package com.service.voucher.controller;

import com.service.voucher.dto.VoucherDto;
import com.service.voucher.service.VoucherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class VouchersController {

    @Autowired
    VoucherService voucherService;

    final static Logger logger = LogManager.getLogger(VouchersController.class);

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
