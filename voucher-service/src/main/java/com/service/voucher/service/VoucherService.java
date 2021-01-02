package com.service.voucher.service;

import com.service.voucher.dto.VoucherDto;
import com.service.voucher.entity.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VoucherService {

    VoucherDto generateVoucher(String phoneNumber);
    List<VoucherDto> getVouchers(String phoneNumber);
}
