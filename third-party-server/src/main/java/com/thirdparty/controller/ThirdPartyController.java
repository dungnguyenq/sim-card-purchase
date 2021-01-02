package com.thirdparty.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/thirdparty")
public class ThirdPartyController {

    @RequestMapping("/newvoucher")
    public String NewVoucher() throws InterruptedException {
        // set random time to delay request
        List<Integer> timeDelays = Arrays.asList(3000, 4000, 8000, 10000, 15000, 29000, 30000, 31000, 35000, 40000, 60000, 80000, 120000, 125000);
        Random rand = new Random();
        int randomTimeDelay = timeDelays.get(rand.nextInt(timeDelays.size()));
        System.out.println(randomTimeDelay);
        Thread.sleep(randomTimeDelay);

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
