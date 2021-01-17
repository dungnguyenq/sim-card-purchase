package com.service.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.voucher.dto.VoucherDto;
import com.service.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class VoucherControllerTest {

    @MockBean
    private VoucherService voucherService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private VoucherDto voucher1;
    private VoucherDto voucher2;
    private VoucherDto voucher3;
    private List<VoucherDto> vouchers = new ArrayList<>();
    private final String phoneNumber = "84968864509";
    private final String voucherCode1 = "skd672A3swK790s";
    private final LocalDateTime createdDate1 = LocalDateTime.of(2021, 1, 7, 18, 4, 21, 1);
    private final String voucherCode2 = "aaB767snjsJo220";
    private final LocalDateTime createdDate2 = createdDate1.plusHours(1);
    private final String voucherCode3 = "UYsmk9829sS9sA3";
    private final LocalDateTime createdDate3 = createdDate1.plusDays(1);

    @BeforeEach
    public void setup(){
        voucher1 = new VoucherDto();
        voucher1.setVoucherCode(voucherCode1);
        voucher1.setCreatedDate(createdDate1);

        voucher2 = new VoucherDto();
        voucher2.setVoucherCode(voucherCode2);
        voucher2.setCreatedDate(createdDate2);

        voucher3 = new VoucherDto();
        voucher3.setVoucherCode(voucherCode3);
        voucher3.setCreatedDate(createdDate3);

        vouchers.add(voucher1);
        vouchers.add(voucher2);
        vouchers.add(voucher3);
    }


    @Test
    public void givenPhoneNumber_WhenGetVouchers_thenReturnJsonArray() throws Exception{
        given(voucherService.getVouchers(phoneNumber)).willReturn(vouchers);

        mockMvc.perform(get("/vouchers?phonenumber={phoneNumber}", phoneNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[0].voucherCode", is(voucher1.getVoucherCode())))
                .andExpect(jsonPath("[0].createdDate", is(voucher1.getCreatedDate().toString())))
                .andExpect(jsonPath("[1].voucherCode", is(voucher2.getVoucherCode())))
                .andExpect(jsonPath("[1].createdDate", is(voucher2.getCreatedDate().toString())))
                .andExpect(jsonPath("[2].voucherCode", is(voucher3.getVoucherCode())))
                .andExpect(jsonPath("[2].createdDate", is(voucher3.getCreatedDate().toString())));
    }

    @Test
    public void generateVoucher_itShouldReturnStatusOk() throws Exception{
        given(voucherService.getVoucher(anyString())).willReturn(voucher1);

        String jsonString = objectMapper.writeValueAsString(voucher1);

        mockMvc.perform(post("/generate")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }
}
