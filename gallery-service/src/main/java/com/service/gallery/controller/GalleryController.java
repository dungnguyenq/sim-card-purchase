package com.service.gallery.controller;

import com.service.gallery.entity.Gallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/")
public class GalleryController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @RequestMapping("/")
    public String home() {
        return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
    }

    @GetMapping("/gallery")
    public ResponseEntity getVouchers(@RequestParam(value = "phonenumber", required = true) String phoneNumber){
        List<Object> vouchers = restTemplate.getForObject(env.getProperty("voucher.service.endpoint") + "/vouchers?phonenumber=" + phoneNumber, List.class);
        Gallery gallery = new Gallery(phoneNumber, vouchers);
        return new ResponseEntity(gallery, HttpStatus.OK);
    }
}
