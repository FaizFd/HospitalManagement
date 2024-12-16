package com.billing_service.billingservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long doctorId;
    private BigDecimal amount;
    private String status; // e.g., "unpaid", "paid"
    private LocalDateTime createdDate;
    private LocalDateTime paidDate;
}
