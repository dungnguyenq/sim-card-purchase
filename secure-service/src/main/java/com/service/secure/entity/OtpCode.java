package com.service.secure.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_code")
public class OtpCode implements Serializable {
    private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "secret_key", nullable = false)
    private String secretKey;

    @Column(name = "created_date", nullable = false)
    protected Instant createdDate;

    @Column(name = "modified_date", nullable = false)
    protected Instant modifiedDate;

    @Column(name = "is_used", nullable = false)
    protected boolean isUsed;

    public OtpCode(){}

    public OtpCode(String phoneNumber, String secretKey, Instant createdDate, Instant modifiedDate) {
        this.phoneNumber = phoneNumber;
        this.secretKey = secretKey;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isUsed = false;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
