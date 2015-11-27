package com.gearservice.service;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.repositories.*;
import com.gearservice.model.request.RequestPreferences;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ChequeService {

    @Autowired ChequeRepository chequeRepository;
    @Autowired DiagnosticRepository diagnosticRepository;
    @Autowired NoteRepository noteRepository;
    @Autowired PhotoRepository photoRepository;
    @Autowired UserRepository userRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired EntityManager em;

    /**
     * Method getCheques call by client-side and return all cheques from database
     * Native query use for create partial object of Cheque � ChequeMin, that has only necessary for client-side fields
     * Call with value "/cheques" and request method GET
     * @return list of all cheques, that database has
     */
    public List<Cheque> getMinChequesList() {
        return chequeRepository.findAll();
    }

    /**
     * Method saveCheque call by client-side with data for cheque.class
     * Call with value "/cheque" and request method POST
     * @param cheque is data for Cheque.class, that was create on client-side
     * @return Cheque, that added
     */
    public Cheque synchronizeCheque(@RequestBody Cheque cheque) {
        Long ID = cheque.getId();

        if(cheque.getBalance().getCheque() == null)
            cheque.getBalance().setCheque(cheque);

        chequeRepository.save(cheque);

        if(ID == null)
            return chequeRepository.findFirstByOrderByIdDesc();
        else
            return chequeRepository.findOne(ID);
    }

    /**
     * Method getCheque call by client-side, when it needs in one cheque for represent
     * Call with value of request "/cheques/{chequeID}" and request method GET
     * @param chequeID is ID of cheque in database, that client-side wants
     * @return Cheque, that client-side was request
     */
    public Cheque getCheque(@PathVariable Long chequeID) {
        return chequeRepository.findById(chequeID);
    }

    /**
     * Method deleteCheque call by client-side, when it needs to delete one cheque
     * Call with value of request "/cheques/{chequeID}" and request method DELETE
     * @param chequeID is ID of cheque in database, that client-side wants to delete
     * @return redirect to main page
     */
    public void deleteCheque(@PathVariable Long chequeID) {
        chequeRepository.delete(chequeID);
        photoRepository.deleteByChequeId(chequeID.toString());
    }

    /**
     * Method addCheques call by client-side, when it needs to fill database few sample cheques
     * Call with value of request "/add" and in default request method GET
     * Should send in response OK status, if code works correct
     * @return redirect to main page
     */
    public ModelAndView addSampleCheques() {
        IntStream.range(0, 3000).parallel().forEach(i -> chequeRepository.save(new Cheque().withRandomData()));
        return new ModelAndView("redirect:/");
    }

    public List<Cheque> attentionCheques() {
        return chequeRepository.findByDiagnosticsIsNull();
    }

    @Transactional(readOnly = true)
    public List<Cheque> attentionChequesByDelay() {
        return chequeRepository.findWithDelay(OffsetDateTime.now().minusDays(3).toString());
    }

    public List<String> getAutocompleteData(String itemName) {
        switch (itemName) {
            case "customerName": return chequeRepository.listOfCustomerNames();
            case "productName": return chequeRepository.listOfProductNames();
            case "modelName": return chequeRepository.listOfModelNames();
            case "serialNumber": return chequeRepository.listOfSerialNumbers();
            case "representativeName": return chequeRepository.listOfRepresentativeNames();
            case "address": return chequeRepository.listOfAddresses();
            case "email": return chequeRepository.listOfEmails();
            default: throw new IllegalArgumentException();
        }
    }

    public List<Cheque> getMinChequesListPre(RequestPreferences request) {
        return chequeRepository.findByRequest(
                request.getIntroducedFrom(),
                request.getIntroducedTo(),
                request.getCustomerName(),
                request.getProductName(),
                request.getModel(),
                request.getSerialNumber(),
                request.getRepresentativeName(),
                request.getSecretary(),
                request.getEngineer(),
                request.getWarrantyStatus(),
                request.getReadyStatus(),
                request.getReturnedToClientStatus(),
                request.getPaidStatus()
        );
    }

    public void readFromExcelToDB() throws Exception {

        FileInputStream excel = new FileInputStream(new File("analytics.xlsx"));

        XSSFWorkbook wb = new XSSFWorkbook(excel);
        XSSFSheet sheet = wb.getSheetAt(0);




    }

}
