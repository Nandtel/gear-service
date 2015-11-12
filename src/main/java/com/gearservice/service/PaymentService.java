package com.gearservice.service;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.Payment;
import com.gearservice.model.repositories.ChequeRepository;
import com.gearservice.model.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PaymentService {

    @Autowired PaymentRepository paymentRepository;
    @Autowired ChequeRepository chequeRepository;

    public List<Payment> getPaymentsOfCheque(Long chequeID) {
        return paymentRepository.findByChequeId(chequeID);
    }

    public List<Payment> synchronizePaymentsOfCheque(Long chequeID, Set<Payment> payments) {
        Cheque cheque = chequeRepository.findOne(chequeID);
        payments.stream().forEach(payment -> payment.setCheque(cheque));
        paymentRepository.save(payments);

        return paymentRepository.findByChequeId(chequeID);
    }

    public void deletePayment(Long paymentID) {paymentRepository.delete(paymentID);}

}
