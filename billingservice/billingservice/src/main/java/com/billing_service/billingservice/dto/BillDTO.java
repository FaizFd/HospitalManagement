package com.billing_service.billingservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    // The client will NOT provide these names; they will be populated by the service.
    private String patientName;
    private String doctorName;
    private BigDecimal amount;
    private String status; // unpaid, paid
    private LocalDateTime createdDate;
    private LocalDateTime paidDate;
}
