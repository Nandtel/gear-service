package com.gearservice.service;

import com.gearservice.model.exchangeRate.ExchangeRate;
import com.gearservice.model.repositories.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired ExchangeRateRepository exchangeRateRepository;

    @Modifying
    @Transactional
    public ExchangeRate getCurrencyRates() {
        String now = LocalDate.now().toString();

        if(!exchangeRateRepository.exists(now))
            return getExchangeRateFromServer();
        else
            return exchangeRateRepository.findOne(now);
    }

    @Transactional(readOnly = true)
    public ExchangeRate getCleanCurrencyRates() {return getExchangeRateFromServer();}

    @Transactional(readOnly = true)
    public List<ExchangeRate> getCurrencyRatesList() {return exchangeRateRepository.findTop4ByOrderByAddDateDesc();}

    @Modifying
    @Transactional
    public void setCurrencyRates(ExchangeRate exchangeRate) {exchangeRateRepository.save(exchangeRate);}

    private ExchangeRate getExchangeRateFromServer() {
        exchangeRateRepository.save(new ExchangeRate().getFromServer("http://minfindnr.ru/", "li#text-12 font"));
        return exchangeRateRepository.findOne(LocalDate.now().toString());
    }
}
