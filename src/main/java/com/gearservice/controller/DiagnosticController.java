package com.gearservice.controller;

import com.gearservice.model.cheque.Diagnostic;
import com.gearservice.service.DiagnosticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Class DiagnosticController is controller, that handle requests of diagnostics
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.2
 * @author Dmitry
 * @since 12.03.2016
 */

@RestController
public class DiagnosticController {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticController.class);

    private final DiagnosticService diagnosticService;

    @Autowired
    public DiagnosticController(DiagnosticService diagnosticService) {
        this.diagnosticService = diagnosticService;
    }

    /**
     * Method addDiagnostic call by client-side, when it needs to add new diagnostic comment to cheque
     * Call with value of request "/api/cheques/{chequeID}/diagnostics" and request method POST
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants add a diagnostic comment
     * @param diagnostic is data for Diagnostic.class, that was create on client-side
     * @param principal is user, that request this action
     */
    @RequestMapping(value = "/api/cheques/{chequeID}/diagnostics", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addDiagnostic(@PathVariable Long chequeID, @RequestBody Diagnostic diagnostic, Principal principal) {
        diagnosticService.addDiagnostic(diagnostic);
        logger.info("User " + principal.getName().toUpperCase() + " has added diagnostic to cheque " + chequeID);
    }

    /**
     * Method deleteDiagnostic call by client-side, when it needs to delete diagnostic comment in cheque
     * Call with value of request "/api/cheques/{chequeID}/diagnostics/{diagnosticID}" and request method DELETE
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants add a diagnostic comment
     * @param diagnosticID is ID of diagnostic in database
     * @param principal is user, that request this action
     */
    @RequestMapping(value = "/api/cheques/{chequeID}/diagnostics/{diagnosticID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteDiagnostic(@PathVariable Long chequeID, @PathVariable Long diagnosticID, Principal principal) {
        diagnosticService.deleteDiagnostic(chequeID, diagnosticID);
        logger.info("User " + principal.getName().toUpperCase() + " has removed diagnostic from cheque " + chequeID);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}/diagnostics", method = RequestMethod.GET)
    public List<Diagnostic> getDiagnostics(@PathVariable Long chequeID) {
        return diagnosticService.getDiagnostics(chequeID);
    }

}
