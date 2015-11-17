package com.gearservice.controller;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChequeController {

    @Autowired ChequeService chequeService;

    @RequestMapping(value = "/api/cheques", method = RequestMethod.GET)
    public List<Cheque> getMinChequesList() {
        return chequeService.getMinChequesList();
    }

    @RequestMapping(value = "/api/cheques", method = RequestMethod.POST)
    public Cheque synchronizeCheque(@RequestBody Cheque cheque) {return chequeService.synchronizeCheque(cheque);}

    @RequestMapping(value = "/api/cheques/{chequeID}", method = RequestMethod.GET)
    public Cheque getCheque(@PathVariable Long chequeID) {return chequeService.getCheque(chequeID);}

    @RequestMapping(value = "/api/cheques/{chequeID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCheque(@PathVariable Long chequeID) {
        chequeService.deleteCheque(chequeID);
    }

    @RequestMapping(value = "/api/attention", method = RequestMethod.GET)
    public List<Cheque> attentionCheques() {return chequeService.attentionCheques();}

    @RequestMapping(value = "/api/delay", method = RequestMethod.GET)
    public List<Cheque> attentionChequesByDelay() {return chequeService.attentionChequesByDelay();}

    @RequestMapping(value = "/api/autocomplete/{itemName}", method = RequestMethod.GET)
    public List<String> autocompleteData(@PathVariable String itemName) {return chequeService.getAutocompleteData(itemName);}

}
