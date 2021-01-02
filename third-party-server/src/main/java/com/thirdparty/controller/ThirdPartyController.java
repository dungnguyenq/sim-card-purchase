package com.thirdparty.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/thirdparty")
public class ThirdPartyController {

    @RequestMapping("/newvoucher")
    public String NewVoucher(){
        Random random = new Random();
        int leftLimit = 48;
        int rightLimit = 122;
        int voucherLength = 15;

        String newVoucher = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(voucherLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return newVoucher;
    }
}
