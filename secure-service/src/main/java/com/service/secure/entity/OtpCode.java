package com.service.secure.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "otp_code")
public class OtpCode implements Serializable {
    private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Setter(AccessLevel.PRIVATE)
    Integer id;

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
}
