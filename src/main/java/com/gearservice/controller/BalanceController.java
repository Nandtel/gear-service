package com.gearservice.controller;

import com.gearservice.model.cheque.Balance;
import com.gearservice.service.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class BalanceController {

    private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);

    @Autowired BalanceService balanceService;

    @RequestMapping(value = "/api/balance/cheque/{chequeID}", method = RequestMethod.GET)
    public Balance getBalanceOfCheque(@PathVariable Long chequeID) {
        return balanceService.getBalanceOfCheque(chequeID);
    }

    @RequestMapping(value = "/api/balance/cheque/{chequeID}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Balance synchronizeBalanceOfCheque(@PathVariable Long chequeID, @RequestBody Balance balance, Principal principal) {
        Balance balanceAfterSync = balanceService.synchronizeBalanceOfCheque(chequeID, balance);
        logger.info("User " + principal.getName().toUpperCase() + " has synchronized balance from cheque №" + chequeID);
        return balanceAfterSync;
    }

}
