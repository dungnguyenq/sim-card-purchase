package com.service.sms.dto;

import lombok.Data;

@Data
public class SmsDto {
    private String phoneNumber;
    private String content;

    public String getPhoneNumberWithPlus() {
        return "+" + phoneNumber;
    }
}
