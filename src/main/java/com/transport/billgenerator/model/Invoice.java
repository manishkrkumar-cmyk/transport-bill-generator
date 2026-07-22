package com.transport.billgenerator.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String invoiceNo;
    private String invoiceDate;

    // Seller Details
    private String sellerName;
    private String sellerAddress;
    private String sellerPhone;
    private String sellerEmail;
    private String sellerGstin;
    private String sellerState;

    // Bill To Details
    private String billToName;
    private String billToAddress;
    private String billToContact;
    private String billToGstin;
    private String billToState;

    // Transport Details
    private String driverName;
    private String driverMobile;
    private String vehicleNumber;

    // Line Items
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> items;

    // Financial Totals
    private Double subTotal;
    private Double packagingFee;
    private Double deliveryFee;
    private Double discount;
    private Double sgst;
    private Double cgst;
    private Double grandTotal;
    private Double receivedAmount;
    private Double balanceAmount;
    private String amountInWords;
}