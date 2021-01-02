package com.service.voucher.dto;

import javax.validation.constraints.NotBlank;

public class GenerateRequest {

    @NotBlank
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
