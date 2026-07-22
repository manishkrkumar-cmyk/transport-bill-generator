package com.transport.billgenerator.repository;

import com.transport.billgenerator.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNo(String invoiceNo);

    List<Invoice> findAllByOrderByIdDesc();
}