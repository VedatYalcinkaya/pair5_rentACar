package com.tobeto.pair5.repositories;

import com.tobeto.pair5.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
