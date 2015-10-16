package com.gearservice.controller;

import com.gearservice.model.currency.Currency;
import com.gearservice.model.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CurrencyController {

    @Autowired CurrencyRepository currencyRepository;

    @RequestMapping(value = "/api/currency-rate", method = RequestMethod.GET)
    public Currency getCurrencyRates() {
        Long now = LocalDate.now().toEpochDay();

        if(!currencyRepository.exists(now))
            currencyRepository.save(new Currency()
                    .forToday()
                    .withRUB()
                    .getFromServer("http://minfindnr.ru/", "li#text-12 font"));

        return currencyRepository.findOne(now);
    }

    @RequestMapping(value = "/api/currency-rate-clean", method = RequestMethod.GET)
    public Currency getCleanCurrencyRates() {
        Long now = LocalDate.now().toEpochDay();

        currencyRepository.save(new Currency()
                .forToday()
                .withRUB()
                .getFromServer("http://minfindnr.ru/", "li#text-12 font"));

        return currencyRepository.findOne(now);
    }

    @RequestMapping(value = "/api/currency-rate-list", method = RequestMethod.GET)
    public List<Currency> getCur() {return currencyRepository.findTop4ByOrderByIdAsc();}

    @RequestMapping(value = "/api/currency-rate-list", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void setCur(@RequestBody List<Currency> list) {currencyRepository.save(list);}
}
