package com.billing_service.billingservice.service;

import com.billing_service.billingservice.dto.BillDTO;
import com.billing_service.billingservice.entity.Bill;
import com.billing_service.billingservice.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String PATIENT_SERVICE_URL = "http://localhost:8081/patients/";
    private static final String DOCTOR_SERVICE_URL = "http://localhost:8082/doctors/";

    public BillDTO createBill(BillDTO billDTO) {
        // We expect only patientId and doctorId (and maybe amount) here.
        Bill bill = new Bill();
        bill.setPatientId(billDTO.getPatientId());
        bill.setDoctorId(billDTO.getDoctorId());
        bill.setAmount(billDTO.getAmount() != null ? billDTO.getAmount() : BigDecimal.ZERO);
        bill.setStatus("unpaid");
        bill.setCreatedDate(LocalDateTime.now());
        bill = billRepository.save(bill);

        // After saving the bill, convert to DTO which will automatically fetch names.
        return toDTO(bill);
    }

    public BillDTO getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        return toDTO(bill);
    }

    public BillDTO updateBill(Long id, BillDTO billDTO) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        // Update only what is provided: patientId, doctorId, amount.
        if (billDTO.getPatientId() != null) {
            bill.setPatientId(billDTO.getPatientId());
        }
        if (billDTO.getDoctorId() != null) {
            bill.setDoctorId(billDTO.getDoctorId());
        }
        if (billDTO.getAmount() != null) {
            bill.setAmount(billDTO.getAmount());
        }

        bill = billRepository.save(bill);
        return toDTO(bill);
    }

    public BillDTO payBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        if ("paid".equalsIgnoreCase(bill.getStatus())) {
            throw new RuntimeException("Bill is already paid");
        }
        bill.setStatus("paid");
        bill.setPaidDate(LocalDateTime.now());
        bill = billRepository.save(bill);
        return toDTO(bill);
    }

    public List<BillDTO> getAllBills(Long patientId, Long doctorId, String status) {
        List<Bill> bills;
        if (patientId != null) {
            bills = billRepository.findByPatientId(patientId);
        } else if (doctorId != null) {
            bills = billRepository.findByDoctorId(doctorId);
        } else if (status != null) {
            bills = billRepository.findByStatus(status);
        } else {
            bills = billRepository.findAll();
        }

        return bills.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public String getBillingSummary() {
        List<Bill> allBills = billRepository.findAll();
        BigDecimal totalRevenue = allBills.stream()
                .filter(b -> "paid".equalsIgnoreCase(b.getStatus()))
                .map(Bill::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long unpaidCount = allBills.stream()
                .filter(b -> "unpaid".equalsIgnoreCase(b.getStatus()))
                .count();

        return "Total Revenue: " + totalRevenue + ", Unpaid Bills: " + unpaidCount;
    }

    private BillDTO toDTO(Bill bill) {
        BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        dto.setPatientId(bill.getPatientId());
        dto.setDoctorId(bill.getDoctorId());
        dto.setAmount(bill.getAmount());
        dto.setStatus(bill.getStatus());
        dto.setCreatedDate(bill.getCreatedDate());
        dto.setPaidDate(bill.getPaidDate());

        // Fetch patient name if patientId is present
        if (bill.getPatientId() != null) {
            Map<String, Object> patientResponse = restTemplate.getForObject(PATIENT_SERVICE_URL + bill.getPatientId(), Map.class);
            if (patientResponse != null && patientResponse.get("name") != null) {
                dto.setPatientName(patientResponse.get("name").toString());
            }
        }

        // Fetch doctor name if doctorId is present
        if (bill.getDoctorId() != null) {
            Map<String, Object> doctorResponse = restTemplate.getForObject(DOCTOR_SERVICE_URL + bill.getDoctorId(), Map.class);
            if (doctorResponse != null && doctorResponse.get("name") != null) {
                dto.setDoctorName(doctorResponse.get("name").toString());
            }
        }

        return dto;
    }
}
