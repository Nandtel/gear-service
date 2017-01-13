package com.gearservice.controller;

import com.gearservice.model.cheque.Balance;
import com.gearservice.service.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Class BalanceController is controller, that handle requests of balance.
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@RestController
public class BalanceController {

    private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);
    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @RequestMapping(value = "/api/balance/cheque/{chequeID}", method = RequestMethod.GET)
    public Balance getBalanceOfCheque(@PathVariable Long chequeID) {
        return balanceService.getBalanceOfCheque(chequeID);
    }

    @RequestMapping(value = "/api/balance/cheque/{chequeID}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Balance synchronizeBalanceOfCheque(@PathVariable Long chequeID, @RequestBody Balance balance, Principal principal) {
        Balance balanceAfterSync = balanceService.synchronizeBalanceOfCheque(chequeID, balance);
        logger.info("User " + principal.getName().toUpperCase() + " has synchronized balance from cheque " + chequeID);
        return balanceAfterSync;
    }

}
