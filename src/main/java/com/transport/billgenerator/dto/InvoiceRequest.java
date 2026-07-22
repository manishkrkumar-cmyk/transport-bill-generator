package com.transport.billgenerator.dto;

import com.transport.billgenerator.model.InvoiceItem;
import lombok.Data;
import java.util.List;

@Data
public class InvoiceRequest {
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
    private List<InvoiceItem> items;

    // Additional Charges
    private Double packagingFee = 0.0;
    private Double deliveryFee = 0.0;
    private Double discount = 0.0;
    private Double receivedAmount = 0.0;
}