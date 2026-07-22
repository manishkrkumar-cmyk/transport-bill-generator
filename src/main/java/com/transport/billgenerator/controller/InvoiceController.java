package com.transport.billgenerator.controller;

import com.transport.billgenerator.dto.InvoiceRequest;
import com.transport.billgenerator.dto.InvoiceResponse;
import com.transport.billgenerator.repository.InvoiceRepository;
import com.transport.billgenerator.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    // 1. POST Endpoint to calculate, save, and return the invoice DTO
    @PostMapping("/generate")
    public ResponseEntity<InvoiceResponse> generateInvoice(@RequestBody InvoiceRequest request) {
        InvoiceResponse response = invoiceService.calculateAndSave(request);
        return ResponseEntity.ok(response);
    }

    // 2. GET Endpoint to fetch a single invoice by its number for the bill history
    // page
    @GetMapping("/{invoiceNo}")
    public ResponseEntity<?> getInvoiceByNo(@PathVariable String invoiceNo) {
        return invoiceRepository.findByInvoiceNo(invoiceNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}