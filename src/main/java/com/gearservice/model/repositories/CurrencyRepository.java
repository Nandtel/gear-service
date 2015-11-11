package com.gearservice.model.repositories;

import com.gearservice.model.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

    List<Currency> findTop4ByOrderByIdDesc();

    @Query(value = "SELECT DISTINCT MIN(id) FROM currency", nativeQuery = true)
    String findMaximumDistantDate();
}
