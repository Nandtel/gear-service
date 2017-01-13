package com.gearservice.controller;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.request.RequestPreferences;
import com.gearservice.service.ChequeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Class ChequeController is controller, that handle requests of cheques.
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@RestController
public class ChequeController {

    private static final Logger logger = LoggerFactory.getLogger(ChequeController.class);
    private final ChequeService chequeService;

    @Autowired
    public ChequeController(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @RequestMapping(value = "/api/cheques/list", method = RequestMethod.POST)
    public Page<Cheque> getMinChequesList(@RequestBody RequestPreferences request, Pageable pageable) {
        return chequeService.getMinChequesList(request, pageable);
    }

    @RequestMapping(value = "/api/cheques", method = RequestMethod.POST)
    public Cheque synchronizeCheque(@RequestBody Cheque cheque, Principal principal) {
        Cheque chequeAfterSync = chequeService.synchronizeCheque(cheque);
        logger.info("User " + principal.getName().toUpperCase() + " has synchronized cheque " + chequeAfterSync.getId());
        return chequeAfterSync;
    }

    @RequestMapping(value = "/api/cheques/{chequeID}", method = RequestMethod.GET)
    public Cheque getCheque(@PathVariable Long chequeID) {return chequeService.getCheque(chequeID);}

    @RequestMapping(value = "/api/cheques/{chequeID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCheque(@PathVariable Long chequeID, Principal principal) {
        chequeService.deleteCheque(chequeID);
        logger.info("User " + principal.getName().toUpperCase() + " has removed cheque " + chequeID);
    }

    @RequestMapping(value = "/api/attention", method = RequestMethod.GET)
    public List<Cheque> attentionCheques() {return chequeService.attentionCheques();}

    @RequestMapping(value = "/api/delay", method = RequestMethod.GET)
    public List<Cheque> attentionChequesByDelay() {return chequeService.attentionChequesByDelay();}

//    @RequestMapping(value = "/sample", method = RequestMethod.GET)
//    @ResponseStatus(value = HttpStatus.OK)
//    public void sample() {chequeService.addSampleCheques();}

//    @RequestMapping(value = "/read", method = RequestMethod.GET)
//    @ResponseStatus(value = HttpStatus.OK)
//    public void readFromExcelToDB() throws Exception {chequeService.readFromExcelToDB();}

}
