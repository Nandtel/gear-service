package com.gearservice.service;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.Diagnostic;
import com.gearservice.model.repositories.ChequeRepository;
import com.gearservice.model.repositories.DiagnosticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DiagnosticService {

    @Autowired DiagnosticRepository diagnosticRepository;
    @Autowired ChequeRepository chequeRepository;

    /**
     * Method addDiagnostic call by client-side, when it needs to add new diagnostic comment to cheque
     * Call with value of request "/cheques/{chequeID}/diagnostic" and request method POST
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants add a diagnostic comment
     * @param diagnostic is data for Diagnostic.class, that was create on client-side
     */
    public void addDiagnostic(@PathVariable Long chequeID, @RequestBody Diagnostic diagnostic) {
        Cheque cheque = chequeRepository.findOne(chequeID);
        cheque.getDiagnostics().add(diagnostic.withDateTime());
        chequeRepository.save(cheque);
    }

    /**
     * Method deleteDiagnostic call by client-side, when it needs to delete diagnostic comment in cheque
     * Call with value of request "/cheques/{chequeID}/diagnostic/{diagnosticID}" and request method DELETE
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param diagnosticID is ID of diagnostic in database, that client-side wants to delete
     */
    public void deleteDiagnostic(@PathVariable Long chequeID, @PathVariable Long diagnosticID) {
        diagnosticRepository.delete(diagnosticID);
    }

}
