package com.service.voucher.repository;

import com.service.voucher.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query("SELECT v FROM Voucher v WHERE v.phoneNumber = ?1")
    List<Voucher> findVouchersByPhoneNumber(String phoneNumber);

    @Query("SELECT v FROM Voucher v WHERE v.phoneNumber = ?1 AND v.createdDate > ?2")
    Voucher findVoucherByPhoneNumberAndCreatedDate(String phoneNumber, LocalDateTime createdDate);

}
