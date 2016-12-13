package com.gearservice.repositories.jpa;

import com.gearservice.model.cheque.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface PaymentRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByExchangeRateAddDateBetween(String findFrom, String findTo);
    Payment findFirstByOrderByExchangeRateAddDateDesc();
    List<Payment> findByBalanceChequeId(Long chequeID);
}
