package com.gearservice.controller;

import com.gearservice.model.cheque.Diagnostic;
import com.gearservice.service.DiagnosticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class DiagnosticController {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticController.class);

    @Autowired DiagnosticService diagnosticService;

    @RequestMapping(value = "/api/cheques/{chequeID}/diagnostics", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addDiagnostic(@PathVariable Long chequeID, @RequestBody Diagnostic diagnostic, Principal principal) {
        diagnosticService.addDiagnostic(chequeID, diagnostic);
        logger.info("User " + principal.getName().toUpperCase() + " has added diagnostic to cheque №" + chequeID);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}/diagnostics/{diagnosticID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteDiagnostic(@PathVariable Long chequeID, @PathVariable Long diagnosticID, Principal principal) {
        diagnosticService.deleteDiagnostic(chequeID, diagnosticID);
        logger.info("User " + principal.getName().toUpperCase() + " has removed diagnostic from cheque №" + chequeID);
    }

}
