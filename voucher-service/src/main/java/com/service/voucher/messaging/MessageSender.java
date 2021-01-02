package com.service.voucher.messaging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    final static Logger logger = LogManager.getLogger(MessageReceiver.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) throws Exception {
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(MessagingConfiguration.topicExchangeName, "voucher.service.com", message);
    }
}
