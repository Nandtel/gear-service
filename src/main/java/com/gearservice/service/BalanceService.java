package com.gearservice.service;

import com.gearservice.model.cheque.Balance;
import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.repositories.BalanceRepository;
import com.gearservice.model.repositories.ChequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    @Autowired BalanceRepository balanceRepository;
    @Autowired ChequeRepository chequeRepository;

    public Balance getBalanceOfCheque(Long chequeID) {
        return balanceRepository.findByChequeId(chequeID);
    }

    public Balance synchronizeBalanceOfCheque(Long chequeID, Balance balance) {

        Cheque cheque = chequeRepository.findOne(chequeID);
        balance.getPayments().stream().forEach(payment -> payment.setBalance(balance));
        balance.setCheque(cheque);
        balanceRepository.save(balance);

        return balanceRepository.findByChequeId(chequeID);
    }
}
