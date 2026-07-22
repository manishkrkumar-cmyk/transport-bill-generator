package com.transport.billgenerator.dto;

import com.transport.billgenerator.model.InvoiceItem;
import lombok.Data;
import java.util.List;

@Data
public class InvoiceResponse {
    private String invoiceNo;
    private String invoiceDate;

    private String sellerName;
    private String sellerAddress;
    private String sellerPhone;
    private String sellerEmail;
    private String sellerGstin;
    private String sellerState;

    private String billToName;
    private String billToAddress;
    private String billToContact;
    private String billToGstin;
    private String billToState;

    private String driverName;
    private String driverMobile;
    private String vehicleNumber;

    private List<InvoiceItem> items;

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