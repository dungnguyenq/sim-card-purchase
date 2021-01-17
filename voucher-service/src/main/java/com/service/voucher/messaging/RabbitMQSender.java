package com.service.voucher.messaging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQSender {

    final static Logger logger = LogManager.getLogger(RabbitMQReceiver.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter jsonMessageConverter;

    @PostConstruct
    private void initRabbit() {
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
    }

    public void sendMessage(String queueName, Object event) throws Exception {
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(queueName, event);
    }
}
