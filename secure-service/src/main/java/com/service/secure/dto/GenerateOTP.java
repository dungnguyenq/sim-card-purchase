package com.service.secure.dto;

import javax.validation.constraints.NotBlank;

public class GenerateOTP {
    @NotBlank
    private String phoneNumber;

    public GenerateOTP() {
    }

    public GenerateOTP(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
