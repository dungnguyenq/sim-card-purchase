package com.service.gallery.dto;

import java.util.List;

public class Gallery {
    private String phoneNumber;
    private int totalVouchers;
    private List<Object> vouchers;

    public Gallery(){};

    public Gallery(String phoneNumber, List<Object> vouchers) {
        this.phoneNumber = phoneNumber;
        this.vouchers = vouchers;
        this.totalVouchers = vouchers.size();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Object> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Object> vouchers) {
        this.vouchers = vouchers;
    }

    public int getTotalVouchers() {
        return totalVouchers;
    }

    public void setTotalVouchers(int totalVouchers) {
        this.totalVouchers = totalVouchers;
    }
}
