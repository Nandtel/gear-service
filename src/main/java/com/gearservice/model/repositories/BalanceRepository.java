package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Balance;
import com.gearservice.model.cheque.Payment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @EntityGraph(value = "balance.full", type = EntityGraph.EntityGraphType.LOAD)
    Balance findByChequeId(Long chequeID);

}
