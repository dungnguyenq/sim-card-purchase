package com.service.secure.repository;

import com.service.secure.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {

    OtpCode findOtpCodeByPhoneNumber(String phoneNumber);
}
