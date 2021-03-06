package com.service.gallery.controller;

import com.service.gallery.dto.GalleryDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class GalleryController {

    final static Logger logger = LogManager.getLogger(GalleryController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${voucher.service.endpoint}")
    private String voucherServiceEndpoint;

    @GetMapping("/")
    public ResponseEntity<GalleryDto> getVouchers(@RequestParam(value = "phonenumber", required = true) String phoneNumber){
        GalleryDto gallery = new GalleryDto();
        try{
            List<Object> vouchers = restTemplate.getForObject(voucherServiceEndpoint + "/" + phoneNumber, List.class);
            gallery.setPhoneNumber(phoneNumber);
            gallery.setVouchers(vouchers);
            gallery.setTotalVouchers(vouchers.size());
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseEntity(gallery, HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity(gallery, HttpStatus.OK);
    }
}
