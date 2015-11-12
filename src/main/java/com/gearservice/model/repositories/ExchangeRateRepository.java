package com.gearservice.model.repositories;

import com.gearservice.model.exchangeRate.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {

    List<ExchangeRate> findTop4ByOrderByAddDateDesc();

    @Query(value = "SELECT DISTINCT MIN(add_date) FROM exchange_rate", nativeQuery = true)
    String findMaximumDistantDate();
}
