package com.service.sms.service;

import com.service.sms.dto.SmsDto;

public interface TwilioService {
    String sendSMS(SmsDto data);
}
