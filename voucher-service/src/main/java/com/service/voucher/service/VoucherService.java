package com.service.voucher.service;

import com.service.voucher.dto.VoucherDto;

import java.util.List;

public interface VoucherService {

    VoucherDto generateVoucher(String phoneNumber);
    List<VoucherDto> getVouchers(String phoneNumber);
    void save(String phoneNumber);
}
