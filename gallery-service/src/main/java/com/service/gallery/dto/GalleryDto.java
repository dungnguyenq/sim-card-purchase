package com.service.gallery.dto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class GalleryDto {
    private String phoneNumber;
    private int totalVouchers;
    private List<Object> vouchers;
}
