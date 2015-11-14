package com.gearservice.controller;

import com.gearservice.model.cheque.Balance;
import com.gearservice.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BalanceController {

    @Autowired BalanceService balanceService;

    @RequestMapping(value = "/api/balance/cheque/{chequeID}", method = RequestMethod.GET)
    public Balance getBalanceOfCheque(@PathVariable Long chequeID) {
        return balanceService.getBalanceOfCheque(chequeID);
    }

    @RequestMapping(value = "/api/balance/cheque/{chequeID}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Balance synchronizeBalanceOfCheque(@PathVariable Long chequeID, @RequestBody Balance balance) {
        return balanceService.synchronizeBalanceOfCheque(chequeID, balance);
    }

}
