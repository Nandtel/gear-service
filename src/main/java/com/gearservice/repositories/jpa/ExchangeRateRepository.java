package com.gearservice.repositories.jpa;

import com.gearservice.model.exchangeRate.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface ExchangeRateRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {

    List<ExchangeRate> findTop4ByOrderByAddDateDesc();

    @Query(value = "SELECT DISTINCT MIN(add_date) FROM exchange_rate", nativeQuery = true)
    String findMaximumDistantDate();
}
