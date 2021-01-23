package com.thirdparty.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    @Value("${voucher.left.limit}")
    private int leftLimit;

    @Value("${voucher.right.limit}")
    private int rightLimit;

    @Value("${voucher.length}")
    private int voucherLength;


    @Override
    public String getVoucherCode() throws Exception {
        // set random time to delay request
        List<Integer> timeDelays = Arrays.asList(0, 3000, 4000, 8000, 10000, 15000, 29000, 30000, 31000, 35000, 40000, 60000, 80000, 120000, 125000);
        Random rand = new Random();
        int randomTimeDelay = timeDelays.get(rand.nextInt(timeDelays.size()));
        System.out.println("Delay Time: " + randomTimeDelay);
        Thread.sleep(randomTimeDelay);

        Random random = new Random();

        String newVoucher = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || (i >= 65 && i <= 90) || i >= 97))
                .limit(voucherLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return newVoucher;
    }
}
