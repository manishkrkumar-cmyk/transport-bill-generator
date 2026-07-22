package com.transport.billgenerator.service;

import com.transport.billgenerator.dto.InvoiceRequest;
import com.transport.billgenerator.dto.InvoiceResponse;
import com.transport.billgenerator.model.Invoice;
import com.transport.billgenerator.model.InvoiceItem;
import com.transport.billgenerator.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private NumberToWordsService numberToWordsService;

    public InvoiceResponse calculateAndSave(InvoiceRequest request) {
        double subTotal = 0.0;
        double totalGst = 0.0;

        List<InvoiceItem> calculatedItems = new ArrayList<>();

        if (request.getItems() != null) {
            for (InvoiceItem item : request.getItems()) {
                double qty = item.getQuantity() != null ? item.getQuantity() : 0;
                double price = item.getPricePerUnit() != null ? item.getPricePerUnit() : 0.0;
                double disc = item.getDiscount() != null ? item.getDiscount() : 0.0;
                double gstRate = item.getGstRate() != null ? item.getGstRate() : 0.0;

                double itemTotal = (qty * price) - disc;
                double itemGst = itemTotal * (gstRate / 100.0);

                item.setTotalAmount(itemTotal);
                calculatedItems.add(item);

                subTotal += itemTotal;
                totalGst += itemGst;
            }
        }

        double pkgFee = request.getPackagingFee() != null ? request.getPackagingFee() : 0.0;
        double delFee = request.getDeliveryFee() != null ? request.getDeliveryFee() : 0.0;
        double overallDisc = request.getDiscount() != null ? request.getDiscount() : 0.0;

        double sgst = totalGst / 2.0;
        double cgst = totalGst / 2.0;

        double grandTotal = subTotal + pkgFee + delFee - overallDisc + totalGst;
        double received = request.getReceivedAmount() != null ? request.getReceivedAmount() : 0.0;
        double balance = grandTotal - received;

        String amountWords = numberToWordsService.convert((long) Math.round(grandTotal));

        // SOLUTION 3: Fetch existing invoice by number or initialize a new entity
        Invoice invoice = invoiceRepository.findByInvoiceNo(request.getInvoiceNo())
                .orElse(new Invoice());

        // Map request fields to invoice object (Works for both INSERT and UPDATE)
        invoice.setInvoiceNo(request.getInvoiceNo());
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setSellerName(request.getSellerName());
        invoice.setSellerAddress(request.getSellerAddress());
        invoice.setSellerPhone(request.getSellerPhone());
        invoice.setSellerEmail(request.getSellerEmail());
        invoice.setSellerGstin(request.getSellerGstin());
        invoice.setSellerState(request.getSellerState());

        invoice.setBillToName(request.getBillToName());
        invoice.setBillToAddress(request.getBillToAddress());
        invoice.setBillToContact(request.getBillToContact());
        invoice.setBillToGstin(request.getBillToGstin());
        invoice.setBillToState(request.getBillToState());

        invoice.setDriverName(request.getDriverName());
        invoice.setDriverMobile(request.getDriverMobile());
        invoice.setVehicleNumber(request.getVehicleNumber());

        invoice.setItems(calculatedItems);
        invoice.setSubTotal(subTotal);
        invoice.setPackagingFee(pkgFee);
        invoice.setDeliveryFee(delFee);
        invoice.setDiscount(overallDisc);
        invoice.setSgst(sgst);
        invoice.setCgst(cgst);
        invoice.setGrandTotal(grandTotal);
        invoice.setReceivedAmount(received);
        invoice.setBalanceAmount(balance);
        invoice.setAmountInWords(amountWords);

        Invoice saved = invoiceRepository.save(invoice);

        return mapToResponse(saved);
    }

    private InvoiceResponse mapToResponse(Invoice invoice) {
        InvoiceResponse res = new InvoiceResponse();
        res.setInvoiceNo(invoice.getInvoiceNo());
        res.setInvoiceDate(invoice.getInvoiceDate());
        res.setSellerName(invoice.getSellerName());
        res.setSellerAddress(invoice.getSellerAddress());
        res.setSellerPhone(invoice.getSellerPhone());
        res.setSellerEmail(invoice.getSellerEmail());
        res.setSellerGstin(invoice.getSellerGstin());
        res.setSellerState(invoice.getSellerState());

        res.setBillToName(invoice.getBillToName());
        res.setBillToAddress(invoice.getBillToAddress());
        res.setBillToContact(invoice.getBillToContact());
        res.setBillToGstin(invoice.getBillToGstin());
        res.setBillToState(invoice.getBillToState());

        res.setDriverName(invoice.getDriverName());
        res.setDriverMobile(invoice.getDriverMobile());
        res.setVehicleNumber(invoice.getVehicleNumber());

        res.setItems(invoice.getItems());
        res.setSubTotal(invoice.getSubTotal());
        res.setPackagingFee(invoice.getPackagingFee());
        res.setDeliveryFee(invoice.getDeliveryFee());
        res.setDiscount(invoice.getDiscount());
        res.setSgst(invoice.getSgst());
        res.setCgst(invoice.getCgst());
        res.setGrandTotal(invoice.getGrandTotal());
        res.setReceivedAmount(invoice.getReceivedAmount());
        res.setBalanceAmount(invoice.getBalanceAmount());
        res.setAmountInWords(invoice.getAmountInWords());
        return res;
    }
}