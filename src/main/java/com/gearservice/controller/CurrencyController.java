package com.gearservice.controller;

import com.gearservice.model.currency.Currency;
import com.gearservice.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurrencyController {

    @Autowired CurrencyService currencyService;

    @RequestMapping(value = "/api/currency-rate", method = RequestMethod.GET)
    public Currency getCurrencyRates() {return currencyService.getCurrencyRates();}

    @RequestMapping(value = "/api/currency-rate-clean", method = RequestMethod.GET)
    public Currency getCleanCurrencyRates() {return currencyService.getCleanCurrencyRates();}

    @RequestMapping(value = "/api/currency-rate-list", method = RequestMethod.GET)
    public List<Currency> getCurrencyRatesList() {return currencyService.getCurrencyRatesList();}

    @RequestMapping(value = "/api/currency-rate", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void setCurrencyRates(@RequestBody Currency currency) {currencyService.setCurrencyRates(currency);}
}
