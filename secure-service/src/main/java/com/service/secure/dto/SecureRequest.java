package com.service.secure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecureRequest {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private int otpCode;
}
