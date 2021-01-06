package com.service.secure.dto;

import javax.validation.constraints.NotBlank;

public class SecureRequest {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private int otpCode;

    public SecureRequest(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(int otpCode) {
        this.otpCode = otpCode;
    }

    public SecureRequest(@NotBlank String phoneNumber, int otpCode) {
        this.phoneNumber = phoneNumber;
        this.otpCode = otpCode;
    }
}
