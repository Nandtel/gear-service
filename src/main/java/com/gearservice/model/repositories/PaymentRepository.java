package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
