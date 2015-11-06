package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCurrencyIdBetween(String findFrom, String findTo);
    Payment findFirstByOrderByCurrencyIdDesc();
}
