package com.service.secure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.secure.dto.GenerateOTP;
import com.service.secure.dto.SecureRequest;
import com.service.secure.dto.SmsDto;
import com.service.secure.service.SecureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class SecureControllerTest {

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private SecureService secureService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private String phoneNumber = "84968864509";
    private GenerateOTP generateOTP;
    private SecureRequest secureRequest;
    private int otp = 180495;
    private String sid = "sid234567653234345";

    @BeforeEach
    public void setup(){
        generateOTP = new GenerateOTP(phoneNumber);
        secureRequest = new SecureRequest(phoneNumber, otp);
    }

    @Test
    public void testIsSecure_itShouldReturnTrueAndStatusOk() throws Exception {
        when(secureService.isSecure(any(SecureRequest.class))).thenReturn(Boolean.TRUE);

        String jsonString = objectMapper.writeValueAsString(secureRequest);

        mockMvc.perform(post("/verify")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testIsSecure_itShouldReturnFalseAndStatusOk() throws Exception {
        when(secureService.isSecure(any(SecureRequest.class))).thenReturn(Boolean.FALSE);

        String jsonString = objectMapper.writeValueAsString(secureRequest);

        mockMvc.perform(post("/verify")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void testGenerateOtp_itShouldReturnStatusOk() throws Exception {
        when(restTemplate.postForObject(any(String.class), any(SmsDto.class), eq(String.class))).thenReturn(sid);

        String jsonString = objectMapper.writeValueAsString(generateOTP);

        mockMvc.perform(post("/generate")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void testGenerateOtp_itShouldReturnStatusBadRequest() throws Exception {
        when(restTemplate.postForObject(any(String.class), any(SmsDto.class), eq(String.class))).thenReturn("");

        String jsonString = objectMapper.writeValueAsString(generateOTP);

        mockMvc.perform(post("/generate")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isBadRequest());
    }


}
