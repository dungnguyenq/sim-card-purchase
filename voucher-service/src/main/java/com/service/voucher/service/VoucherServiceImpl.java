package com.service.voucher.service;

import com.service.voucher.dto.VoucherDto;
import com.service.voucher.entity.Voucher;
import com.service.voucher.helper.ThirdPartyHelper;
import com.service.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    Environment env;

    @Override
    public VoucherDto generateVoucher(String phoneNumber) {
        String voucherCode = ThirdPartyHelper.get(env.getProperty("thirdparty.endpoint"));
        Voucher v = new Voucher();
        LocalDateTime now = LocalDateTime.now();
        v.setPhoneNumber(phoneNumber);
        v.setVoucherCode(voucherCode);
        v.setCreatedDate(now);
        voucherRepository.save(v);

        VoucherDto voucherDto = new VoucherDto(voucherCode, now);
        return voucherDto;
    }

    @Override
    public List<VoucherDto> getVouchers(String phoneNumber) {
        List<VoucherDto> results = new ArrayList<>();
        List<Voucher> vouchers = voucherRepository.findVouchersByPhoneNumber(phoneNumber);

        if (!vouchers.isEmpty()){
            results = vouchers.stream()
                    .map(v -> new VoucherDto(v.getVoucherCode(), v.getCreatedDate()))
                    .collect(Collectors.toList());
        }
        return results;
    }
}
