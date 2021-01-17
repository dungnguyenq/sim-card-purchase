package com.service.voucher.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PhoneDto {

    @NotBlank
    private String phoneNumber;
}
