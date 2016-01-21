package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Balance;
import com.gearservice.model.cheque.Payment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface BalanceRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @EntityGraph(value = "balance.full", type = EntityGraph.EntityGraphType.LOAD)
    Balance findByChequeId(Long chequeID);

}
