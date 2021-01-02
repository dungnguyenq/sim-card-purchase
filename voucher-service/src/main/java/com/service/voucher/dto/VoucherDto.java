package com.service.voucher.dto;

import java.time.LocalDateTime;

public class VoucherDto {
    private String voucherCode;
    private LocalDateTime createdDate;

    public VoucherDto(){}

    public VoucherDto(String voucherCode, LocalDateTime createdDate) {
        this.voucherCode = voucherCode;
        this.createdDate = createdDate;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
