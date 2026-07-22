package com.transport.billgenerator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/invoices") // <--- Changed mapping to prevent duplicate route conflict
public class InvoiceApiController {

    @PostMapping("/generate")
    public ResponseEntity<?> generateInvoice(@RequestBody Map<String, Object> invoiceData) {
        return ResponseEntity.ok(invoiceData);
    }
}