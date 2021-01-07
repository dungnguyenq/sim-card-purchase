package com.service.voucher.service;

import com.service.voucher.dto.VoucherDto;
import com.service.voucher.entity.Voucher;
import com.service.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class VoucherServiceTest {

    @TestConfiguration
    static class VoucherServiceImplTestConfiguration{
        @Bean
        public VoucherService voucherService() {
            return new VoucherServiceImpl();
        }
    }

    @Autowired
    private ApplicationContext context;

    @Autowired
    private VoucherService voucherService;

    @MockBean
    private VoucherRepository voucherRepository;

    private Voucher voucher1;
    private Voucher voucher2;
    private Voucher voucher3;
    private List<Voucher> vouchers = new ArrayList<>();
    private final String phoneNumber = "84968864509";
    private final String voucherCode1 = "skd672A3swK790s";
    private final LocalDateTime createdDate1 = LocalDateTime.now();
    private final String voucherCode2 = "aaB767snjsJo220";
    private final LocalDateTime createdDate2 = LocalDateTime.now().minusHours(1);
    private final String voucherCode3 = "UYsmk9829sS9sA3";
    private final LocalDateTime createdDate3 = LocalDateTime.now().minusDays(1);

    @BeforeEach
    public void setup(){
        voucher1 = new Voucher();
        voucher1.setPhoneNumber(phoneNumber);
        voucher1.setVoucherCode(voucherCode1);
        voucher1.setCreatedDate(createdDate1);

        voucher2 = new Voucher();
        voucher2.setPhoneNumber(phoneNumber);
        voucher2.setVoucherCode(voucherCode2);
        voucher2.setCreatedDate(createdDate2);

        voucher3 = new Voucher();
        voucher3.setPhoneNumber(phoneNumber);
        voucher3.setVoucherCode(voucherCode3);
        voucher3.setCreatedDate(createdDate3);

        vouchers.add(voucher1);
        vouchers.add(voucher2);
        vouchers.add(voucher3);

        Mockito.when(voucherRepository.findVouchersByPhoneNumber(phoneNumber)).thenReturn(vouchers);

        Mockito.when(voucherRepository.save(voucher1)).thenReturn(voucher1);

    }


    @Test
    public void testFindVouchersByPhoneNumber_thenVouchersListShouldBeReturned(){
       List<VoucherDto> foundVouchers = voucherService.getVouchers(phoneNumber);

       Assertions.assertNotNull(foundVouchers);
       Assertions.assertEquals(foundVouchers.size(), 3);
    }

    @Test
    public void testSaveVoucher_thenVoucherShouldBeReturned(){
        Voucher foundVoucher = voucherService.save(phoneNumber);

        Assertions.assertNotNull(foundVoucher);
        Assertions.assertEquals(phoneNumber, foundVoucher.getPhoneNumber());
    }

    @Test
    public void testGetVoucherWithLimitTime_thenVoucherShouldBeReturned(){
        Voucher saveVoucher = voucherRepository.save(voucher1);
        Voucher foundVoucher = voucherService.getVoucherWithLimitTime(phoneNumber, createdDate1.minusSeconds(10), 30);

        Assertions.assertNotNull(foundVoucher);
        Assertions.assertEquals(foundVoucher.getCreatedDate(), createdDate1);
        Assertions.assertEquals(foundVoucher.getVoucherCode(), voucherCode1);
        Assertions.assertEquals(foundVoucher.getPhoneNumber(), phoneNumber);
    }
}
