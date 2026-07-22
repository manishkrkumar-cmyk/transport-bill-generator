package com.transport.billgenerator.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "invoice_items")
@Data
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private String hsnCode;
    private Integer quantity;
    private String unit;
    private Double pricePerUnit;
    private Double discount;
    private Double gstRate;
    private Double totalAmount;
}