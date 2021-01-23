package com.service.sms.service;

import com.service.sms.dto.SmsDto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioServiceImpl implements TwilioService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.from.phone}")
    private String fromPhone;

    @Override
    public String sendSMS(SmsDto data) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(data.getPhoneNumberWithPlus()),
                new com.twilio.type.PhoneNumber(fromPhone),
                data.getContent()).create();

        return message.getSid();
    }
}
