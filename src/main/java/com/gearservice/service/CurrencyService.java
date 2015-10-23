package com.gearservice.service;

import com.gearservice.model.currency.Currency;
import com.gearservice.model.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyService {

    @Autowired CurrencyRepository currencyRepository;

    public static CurrencyService make() {
        return new CurrencyService();
    }

    public Currency getCurrencyRates() {
        String now = LocalDate.now().toString();

        if(!currencyRepository.exists(now))
            currencyRepository.save(new Currency()
                    .forToday()
                    .withRUB()
                    .getFromServer("http://minfindnr.ru/", "li#text-12 font"));

        return currencyRepository.findOne(now);
    }

    public Currency getCleanCurrencyRates() {
        String now = LocalDate.now().toString();

        currencyRepository.save(new Currency()
                .forToday()
                .withRUB()
                .getFromServer("http://minfindnr.ru/", "li#text-12 font"));

        return currencyRepository.findOne(now);
    }

    public List<Currency> getCurrencyRatesList() {return currencyRepository.findTop4ByOrderByIdAsc();}

    public void setCurrencyRates(Currency currency) {currencyRepository.save(currency);}
}
