package com.gearservice.controller;

import com.gearservice.model.exchangeRate.ExchangeRate;
import com.gearservice.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExchangeRateController {

    @Autowired
    ExchangeRateService exchangeRateService;

    @RequestMapping(value = "/api/currency-rate", method = RequestMethod.GET)
    public ExchangeRate getExchangeRate() {return exchangeRateService.getCurrencyRates();}

    @RequestMapping(value = "/api/currency-rate-clean", method = RequestMethod.GET)
    public ExchangeRate getCleanExchangeRate() {return exchangeRateService.getCleanCurrencyRates();}

    @RequestMapping(value = "/api/currency-rate-list", method = RequestMethod.GET)
    public List<ExchangeRate> getExchangeRatesList() {return exchangeRateService.getCurrencyRatesList();}

    @RequestMapping(value = "/api/currency-rate", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void setExchangeRates(@RequestBody ExchangeRate exchangeRate) {
        exchangeRateService.setCurrencyRates(exchangeRate);}
}
