package com.billing_service.billingservice.controller;

import com.billing_service.billingservice.dto.BillDTO;
import com.billing_service.billingservice.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping
    public BillDTO createBill(@Valid @RequestBody BillDTO billDTO) {
        return billingService.createBill(billDTO);
    }

    @GetMapping("/{id}")
    public BillDTO getBill(@PathVariable Long id) {
        return billingService.getBillById(id);
    }

    @PutMapping("/{id}")
    public BillDTO updateBill(@PathVariable Long id, @Valid @RequestBody BillDTO billDTO) {
        return billingService.updateBill(id, billDTO);
    }

    @PutMapping("/{id}/pay")
    public BillDTO payBill(@PathVariable Long id) {
        return billingService.payBill(id);
    }

    @GetMapping
    public List<BillDTO> getAllBills(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String status) {
        return billingService.getAllBills(patientId, doctorId, status);
    }

    @GetMapping("/summary")
    public String getBillingSummary() {
        return billingService.getBillingSummary();
    }
}
