package com.thirdparty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    @Autowired
    private Environment env;

    @Override
    public String getVoucherCode() throws Exception {
        // set random time to delay request
        List<Integer> timeDelays = Arrays.asList(3000, 4000, 8000, 10000, 15000, 29000, 30000, 31000, 35000, 40000, 60000, 80000, 120000, 125000);
        Random rand = new Random();
        int randomTimeDelay = timeDelays.get(rand.nextInt(timeDelays.size()));
        System.out.println("Delay Time: " + randomTimeDelay);
        Thread.sleep(randomTimeDelay);

        Random random = new Random();
        int leftLimit = Integer.valueOf(env.getProperty("voucher.left.limit"));
        int rightLimit = Integer.valueOf(env.getProperty("voucher.right.limit"));
        int voucherLength = Integer.valueOf(env.getProperty("voucher.length"));

        String newVoucher = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(voucherLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return newVoucher;
    }
}
