package com.service.voucher.service;

import com.service.voucher.dto.VoucherDto;
import com.service.voucher.entity.Voucher;

import java.time.LocalDateTime;
import java.util.List;

public interface VoucherService {

    VoucherDto generateVoucher(String phoneNumber) throws Exception;
    List<VoucherDto> getVouchers(String phoneNumber);
    void save(String phoneNumber);
    Voucher getVoucherWithLimitTime(String phoneNumber, LocalDateTime dateTime, int totalSeconds);
}
