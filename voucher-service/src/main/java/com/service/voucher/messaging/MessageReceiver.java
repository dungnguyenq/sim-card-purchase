package com.service.voucher.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.voucher.dto.EventDto;
import com.service.voucher.dto.SmsDto;
import com.service.voucher.entity.Voucher;
import com.service.voucher.service.VoucherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

@Component
public class MessageReceiver {

    final static Logger logger = LogManager.getLogger(MessageReceiver.class);

    @Autowired
    VoucherService voucherService;

    @Autowired
    MessageSender messageSender;

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues=MessagingConfiguration.voucherQueueName)
    public void handleVoucherMessage(@Payload String event) {
        logger.info("Received <" + event + ">");
        logger.info("Save new record!!!");
        try{
            voucherService.save(event);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

    @RabbitListener(queues=MessagingConfiguration.eventQueueName)
    public void handleEventMessage(@Payload EventDto event) {
        String phoneNumber = event.getPhoneNumber();
        LocalDateTime dateTime = event.getDateTime();
        logger.info("Received <" + phoneNumber + " - " + dateTime + ">");

        Voucher voucher = voucherService.getVoucherWithLimitTime(phoneNumber, dateTime, 120);

        SmsDto smsDto = new SmsDto(phoneNumber, "<YOUR VOUCHER CAN NOT BE GENERATE ON TIME, PLEASE CONTACT US TO GET YOUR VOUCHER!!!>");
        if (voucher != null){
            smsDto.setContent("YOUR VOUCHER CODE: " + voucher.getVoucherCode());
        } else{
            logger.error("Can not generate Voucher on time");
        }

        try{
            logger.info("Send message to SMS queue");
            messageSender.sendMessage(MessagingConfiguration.smsQueueName, smsDto);
        } catch (Exception ex){
            logger.error("Generate voucher for " + phoneNumber + " can not be done!!!");
            logger.error(ex.getMessage());
        }
    }
}
