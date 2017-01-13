package com.gearservice.controller;

import com.gearservice.model.exchangeRate.ExchangeRate;
import com.gearservice.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class ExchangeRateController is controller, that handle requests of exchange rates.
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@RestController
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

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
