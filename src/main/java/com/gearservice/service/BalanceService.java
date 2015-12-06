package com.gearservice.service;

import com.gearservice.model.cheque.Balance;
import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.repositories.BalanceRepository;
import com.gearservice.model.repositories.ChequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceService {

    @Autowired BalanceRepository balanceRepository;
    @Autowired ChequeRepository chequeRepository;

    @Transactional(readOnly = true)
    public Balance getBalanceOfCheque(Long chequeID) {
        return balanceRepository.findByChequeId(chequeID);
    }

    @Modifying
    @Transactional
    public Balance synchronizeBalanceOfCheque(Long chequeID, Balance balance) {

        Cheque cheque = chequeRepository.findOne(chequeID);

        if (balance.getPayments() != null)
            balance.getPayments().stream().forEach(payment -> payment.setBalance(balance));

        balance.setCheque(cheque);
        balanceRepository.save(balance);

        return balanceRepository.findByChequeId(chequeID);
    }
}
