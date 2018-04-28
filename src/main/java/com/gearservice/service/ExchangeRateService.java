package com.gearservice.service;

import com.gearservice.model.exchangeRate.ExchangeRate;
import com.gearservice.repositories.jpa.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

/**
 * Class ExchangeRateService is service, that handle ExchangeRateController
 * Use @Autowired for connect to necessary repositories
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * Method getCurrencyRates
     * return current exchange rates: if it exist in DB - return it, otherwise - request from website
     * @return current exchange rates
     */
    @Modifying
    @Transactional
    public ExchangeRate getCurrencyRates() {
        String now = LocalDate.now().toString();

        if(!exchangeRateRepository.existsById(now))
            return getExchangeRateFromServer();
        else
            return exchangeRateRepository.findById(now).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Method getCleanCurrencyRates return clean(not from DB) exchange rates
     * @return clean(not from DB) exchange rates
     */
    @Transactional(readOnly = true)
    public ExchangeRate getCleanCurrencyRates() {return getExchangeRateFromServer();}

    /**
     * Method getCurrencyRatesList return list of exchange rates - top 4
     * @return list of exchange rates - top 4
     */
    @Transactional(readOnly = true)
    public List<ExchangeRate> getCurrencyRatesList() {return exchangeRateRepository.findTop4ByOrderByAddDateDesc();}

    /**
     * Method setCurrencyRates save exchange rates manually from user to DB
     * @param exchangeRate of user's manually input
     */
    @Modifying
    @Transactional
    public void setCurrencyRates(ExchangeRate exchangeRate) {exchangeRateRepository.save(exchangeRate);}

    /**
     * Method getExchangeRateFromServer get from website exchange currency rates
     * @return exchange currency rates from website
     */
    private ExchangeRate getExchangeRateFromServer() {
        ExchangeRate exchangeRate;
        try {
            exchangeRate = new ExchangeRate().getFromServer(
                    "https://crb-dnr.ru/",
                    "div.pane-exchange-rates div.views-field tr.odd td:nth-child(2)"
            );
        } catch (Exception e) {
            e.printStackTrace();
            exchangeRate = new ExchangeRate();
        }
        exchangeRateRepository.save(exchangeRate);
        return exchangeRateRepository.findById(LocalDate.now().toString()).orElseThrow(EntityNotFoundException::new);
    }
}
