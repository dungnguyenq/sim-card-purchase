package com.service.voucher.messaging;

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

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

@Component
public class RabbitMQReceiver {

    final static Logger logger = LogManager.getLogger(RabbitMQReceiver.class);

    @Autowired
    VoucherService voucherService;

    @Autowired
    RabbitMQSender messageRabbitMQSender;

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener(queues= RabbitMQConfiguration.voucherQueueName)
    public void handleVoucherMessage(@Payload String event) {
        logger.info("Received <" + event + ">");
        logger.info("Save new record!!!");
        try{
            voucherService.save(event);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

    @RabbitListener(queues= RabbitMQConfiguration.eventQueueName)
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
            messageRabbitMQSender.sendMessage(RabbitMQConfiguration.smsQueueName, smsDto);
        } catch (Exception ex){
            logger.error("Generate voucher for " + phoneNumber + " can not be done!!!");
            logger.error(ex.getMessage());
        }
    }
}
