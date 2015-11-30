package com.gearservice.service;

import com.gearservice.model.exchangeRate.ExchangeRate;
import com.gearservice.model.repositories.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired ExchangeRateRepository exchangeRateRepository;

    public ExchangeRate getCurrencyRates() {
        String now = LocalDate.now().toString();

        if(!exchangeRateRepository.exists(now))
            return getExchangeRateFromServer();
        else
            return exchangeRateRepository.findOne(now);
    }

    public ExchangeRate getCleanCurrencyRates() {
        return getExchangeRateFromServer();
    }

    private ExchangeRate getExchangeRateFromServer() {
        exchangeRateRepository.save(new ExchangeRate().getFromServer("http://minfindnr.ru/", "li#text-12 font"));
        return exchangeRateRepository.findOne(LocalDate.now().toString());
    }

    public List<ExchangeRate> getCurrencyRatesList() {return exchangeRateRepository.findTop4ByOrderByAddDateDesc();}
    public void setCurrencyRates(ExchangeRate exchangeRate) {exchangeRateRepository.save(exchangeRate);}
}
