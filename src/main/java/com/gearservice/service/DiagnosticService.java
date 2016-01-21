package com.gearservice.service;

import com.gearservice.model.cheque.Diagnostic;
import com.gearservice.model.repositories.ChequeRepository;
import com.gearservice.model.repositories.DiagnosticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Class DiagnosticService is service, that handle DiagnosticController.
 * Use @Autowired for connect to necessary repositories
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Service
public class DiagnosticService {

    @Autowired DiagnosticRepository diagnosticRepository;
    @Autowired ChequeRepository chequeRepository;

    /**
     * Method addDiagnostic add diagnostic to DB with current DateTime
     * @param chequeID is ID of cheque in database, in that client-side wants add a diagnostic comment
     * @param diagnostic is data for Diagnostic.class, that was create on client-side
     */
    @Modifying
    @Transactional
    public void addDiagnostic(@PathVariable Long chequeID, @RequestBody Diagnostic diagnostic) {
        diagnosticRepository.save(diagnostic.withDateTime());
    }

    /**
     * Method deleteDiagnostic delete diagnostic from DB
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param diagnosticID is ID of diagnostic in database, that client-side wants to delete
     */
    @Modifying
    @Transactional
    public void deleteDiagnostic(@PathVariable Long chequeID, @PathVariable Long diagnosticID) {
        diagnosticRepository.delete(diagnosticID);
    }

}
