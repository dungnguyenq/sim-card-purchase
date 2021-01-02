package com.service.voucher.service;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.FakeTimeLimiter;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import com.service.voucher.dto.VoucherDto;
import com.service.voucher.entity.Voucher;
import com.service.voucher.helper.ThirdPartyHelper;
import com.service.voucher.messaging.MessageSender;
import com.service.voucher.repository.VoucherRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    Environment env;

    @Autowired
    MessageSender messageSender;

    @Override
    public VoucherDto generateVoucher(String phoneNumber) {
        LocalDateTime now = LocalDateTime.now();
        try{
            messageSender.sendMessage(phoneNumber);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        Voucher voucher = new Voucher();
        TimeLimiter limiter = SimpleTimeLimiter.create(Executors.newCachedThreadPool());
        try {
            voucher = limiter.callWithTimeout(new Callable<Voucher>() {
                @Override
                public Voucher call() throws Exception {
                    Voucher temp = null;
                    while (temp == null){
                        temp = voucherRepository.findVoucherByPhoneNumberAndCreatedDate(phoneNumber, now);
                    }
                    return temp;
                }
            }, 30, TimeUnit.SECONDS);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        VoucherDto voucherDto = new VoucherDto(voucher.getVoucherCode(), voucher.getCreatedDate());
        return voucherDto;
    }

    @Override
    public List<VoucherDto> getVouchers(String phoneNumber) {
        List<VoucherDto> results = new ArrayList<>();
        List<Voucher> vouchers = voucherRepository.findVouchersByPhoneNumber(phoneNumber);

        if (!vouchers.isEmpty()){
            results = vouchers.stream()
                    .map(v -> new VoucherDto(v.getVoucherCode(), v.getCreatedDate()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    @Override
    public void save(String phoneNumber) {
        String voucherCode = ThirdPartyHelper.get(env.getProperty("thirdparty.endpoint"));
        if (!Strings.isNullOrEmpty(voucherCode)){
            Voucher v = new Voucher();
            LocalDateTime now = LocalDateTime.now();
            v.setPhoneNumber(phoneNumber);
            v.setVoucherCode(voucherCode);
            v.setCreatedDate(now);
            voucherRepository.save(v);
        }
    }
}
