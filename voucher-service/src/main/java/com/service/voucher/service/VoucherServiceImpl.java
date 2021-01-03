package com.service.voucher.service;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import com.service.voucher.dto.EventDto;
import com.service.voucher.dto.VoucherDto;
import com.service.voucher.entity.Voucher;
import com.service.voucher.helper.ThirdPartyHelper;
import com.service.voucher.messaging.MessageSender;
import com.service.voucher.messaging.MessagingConfiguration;
import com.service.voucher.repository.VoucherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    final static Logger logger = LogManager.getLogger(VoucherServiceImpl.class);

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    Environment env;

    @Autowired
    MessageSender messageSender;

    @Override
    public VoucherDto generateVoucher(String phoneNumber) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        try{
            messageSender.sendMessage(MessagingConfiguration.voucherQueueName, phoneNumber);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }

        Voucher voucher = getVoucherWithLimitTime(phoneNumber, now, 30);
        if (voucher == null){
            EventDto eventDto = new EventDto(phoneNumber, now);
            messageSender.sendMessage(MessagingConfiguration.eventQueueName, eventDto);
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

    @Override
    public Voucher getVoucherWithLimitTime(String phoneNumber, LocalDateTime dateTime, int totalSeconds) {
        Voucher voucher = new Voucher();
        TimeLimiter limiter = SimpleTimeLimiter.create(Executors.newCachedThreadPool());
        try {
            voucher = limiter.callWithTimeout(new Callable<Voucher>() {
                @Override
                public Voucher call() throws Exception {
                    Voucher temp = null;
                    while (temp == null){
                        temp = voucherRepository.findVoucherByPhoneNumberAndCreatedDate(phoneNumber, dateTime);
                    }
                    return temp;
                }
            }, totalSeconds, TimeUnit.SECONDS);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return null;
        }
        return voucher;
    }
}
