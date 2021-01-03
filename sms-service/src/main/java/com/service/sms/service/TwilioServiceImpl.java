package com.service.sms.service;

import com.service.sms.dto.SmsDto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class TwilioServiceImpl implements TwilioService {

    @Autowired
    Environment env;

    @Override
    public String sendSMS(SmsDto data) throws Exception{
        String accountSid = env.getProperty("twilio.account.sid");
        String authToken = env.getProperty("twilio.auth.token");
        String fromPhone = env.getProperty("twilio.from.phone");

        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(data.getPhoneNumberWithPlus()),
                new com.twilio.type.PhoneNumber(fromPhone),
                data.getContent()).create();

        return message.getSid();
    }
}
