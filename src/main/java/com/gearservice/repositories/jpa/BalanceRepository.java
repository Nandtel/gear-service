package com.gearservice.repositories.jpa;

import com.gearservice.model.cheque.Balance;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

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
