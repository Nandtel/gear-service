package com.gearservice.model.repositories;

import com.gearservice.model.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findTop4ByOrderByIdAsc();
}
