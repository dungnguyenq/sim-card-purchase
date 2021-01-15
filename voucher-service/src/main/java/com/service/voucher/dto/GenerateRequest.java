package com.service.voucher.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GenerateRequest {

    @NotBlank
    private String phoneNumber;
}
