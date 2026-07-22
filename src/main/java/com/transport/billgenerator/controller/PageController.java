package com.transport.billgenerator.controller;

import com.transport.billgenerator.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping({ "/", "/login" })
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    // Ensures /invoice-print renders templates/invoice-print.html
    @GetMapping("/invoice-print")
    public String invoicePrint() {
        return "invoice-print";
    }

    // Fetches all saved bills from MySQL and renders templates/invoice-list.html
    @GetMapping("/invoices")
    public String showInvoiceList(Model model) {
        model.addAttribute("invoices", invoiceRepository.findAllByOrderByIdDesc());
        return "invoice-list";
    }
}