package com.service.sms.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.sms.dto.SmsDto;
import com.service.sms.service.TwilioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {

    final static Logger logger = LogManager.getLogger(RabbitMQReceiver.class);

    @Autowired
    TwilioService twilioService;

    @RabbitListener(queues="sms-service-queue")
    public void handleMessage(@Payload SmsDto event) throws JsonProcessingException {
        logger.info("Received data from queue");
        System.out.println(event.getPhoneNumber() + " - " + event.getContent());

        try {
            String data = twilioService.sendSMS(event);
            logger.info("Sent SMS successfully, sid: " + data);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }
}
