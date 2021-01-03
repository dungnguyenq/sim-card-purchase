package com.service.sms.dto;

public class SmsDto {
    private String phoneNumber;
    private String content;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoneNumberWithPlus() {
        return "+" + phoneNumber;
    }
}
