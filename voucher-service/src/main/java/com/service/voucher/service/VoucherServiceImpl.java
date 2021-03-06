package com.service.voucher.service;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import com.google.common.util.concurrent.TimeLimiter;
import com.service.voucher.dto.EventDto;
import com.service.voucher.dto.VoucherDto;
import com.service.voucher.entity.Voucher;
import com.service.voucher.messaging.RabbitMQSender;
import com.service.voucher.messaging.RabbitMQConfiguration;
import com.service.voucher.repository.VoucherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    RabbitMQSender rabbitMQSender;

    @Autowired
    private ThirdPartyService thirdPartyService;

    @Override
    public VoucherDto getVoucher(String phoneNumber) {
        LocalDateTime now = LocalDateTime.now();
        try{
            rabbitMQSender.sendMessage(RabbitMQConfiguration.voucherQueueName, phoneNumber);
        } catch (Exception ex){
            logger.error("Can not send message {" + phoneNumber + "} to voucherQueue");
            logger.error(ex.getMessage());
        }

        Voucher voucher = getVoucherWithLimitTime(phoneNumber, now, 29);
        if (voucher == null){
            EventDto eventDto = new EventDto(phoneNumber, now);
            try{
                logger.info("The Voucher will be send to " + phoneNumber);
                rabbitMQSender.sendMessage(RabbitMQConfiguration.eventQueueName, eventDto);
            } catch (Exception ex){
                logger.error("Can not send message {" + phoneNumber + ", " + now + "} to eventQueue");
                logger.error(ex.getMessage());
            }
            return null;
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
    public Voucher save(String phoneNumber) {
        String voucherCode = thirdPartyService.generateVoucherCode();
        if (!Strings.isNullOrEmpty(voucherCode)){
            Voucher v = new Voucher();
            LocalDateTime now = LocalDateTime.now();
            v.setPhoneNumber(phoneNumber);
            v.setVoucherCode(voucherCode);
            v.setCreatedDate(now);
            return voucherRepository.save(v);
        }
        return null;
    }

    @Override
    public Voucher getVoucherWithLimitTime(String phoneNumber, LocalDateTime dateTime, int totalSeconds) {
        Voucher voucher;
        TimeLimiter limiter = SimpleTimeLimiter.create(Executors.newCachedThreadPool());
        try {
            voucher = limiter.callWithTimeout(() -> {
                    Voucher temp = null;
                    while (temp == null){
                        temp = voucherRepository.findVoucherByPhoneNumberAndCreatedDate(phoneNumber, dateTime);
                    }
                    return temp;
            }, totalSeconds, TimeUnit.SECONDS);
        } catch (Exception ex){
            logger.error("Can not found the voucher of " + phoneNumber + " that created after: " + dateTime);
            logger.error(ex.getMessage());
            return null;
        }
        return voucher;
    }
}
