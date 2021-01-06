package com.service.secure.dto;

public class SmsDto {
    private String phoneNumber;
    private String content;

    public SmsDto(String phoneNumber, String content) {
        this.phoneNumber = phoneNumber;
        this.content = content;
    }

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
