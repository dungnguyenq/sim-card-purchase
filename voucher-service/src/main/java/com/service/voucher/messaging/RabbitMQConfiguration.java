package com.service.voucher.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.Arrays;
import java.util.List;


@Configuration
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

    static final String topicExchangeName = "service-topic";

    public static final String voucherQueueName = "voucher-service-queue";

    public static final String smsQueueName = "sms-service-queue";

    public static final String eventQueueName = "event-queue";

    @Bean
    Queue voucherQueue() {
        return new Queue(voucherQueueName, false);
    }

    @Bean
    Queue smsQueue() {
        return new Queue(smsQueueName, false);
    }

    @Bean
    Queue eventQueue() {
        return new Queue(eventQueueName, false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(topicExchangeName);
    }

    @Bean
    public List<Binding> binding() {
        return Arrays.asList(
                BindingBuilder.bind(voucherQueue()).to(exchange()),
                BindingBuilder.bind(smsQueue()).to(exchange()),
                BindingBuilder.bind(eventQueue()).to(exchange()));
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Autowired
    public ConnectionFactory connectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    public RabbitMQReceiver eventResultHandler() {
        return new RabbitMQReceiver();
    }

    @Override
    public void configureRabbitListeners(
            RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }


}
