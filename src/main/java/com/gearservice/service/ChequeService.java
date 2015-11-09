package com.gearservice.service;

import com.gearservice.model.cheque.*;

import com.gearservice.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

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
    public List<ChequeMin> getMinChequesList() {
        return chequeRepository.getListOfCompactCheques();
    }

    /**
     * Method saveCheque call by client-side with data for cheque.class
     * Call with value "/cheque" and request method POST
     * @param cheque is data for Cheque.class, that was create on client-side
     * @return Cheque, that added
     */
    public Cheque synchronizeCheque(@RequestBody Cheque cheque) {
        Long ID = cheque.getId();

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
        return chequeRepository.findOne(chequeID);
    }

    /**
     * Method deleteCheque call by client-side, when it needs to delete one cheque
     * Call with value of request "/cheques/{chequeID}" and request method DELETE
     * @param chequeID is ID of cheque in database, that client-side wants to delete
     * @return redirect to main page
     */
    public void deleteCheque(@PathVariable Long chequeID) {
        chequeRepository.delete(chequeID);
    }

    /**
     * Method addDiagnostic call by client-side, when it needs to add new diagnostic comment to cheque
     * Call with value of request "/cheques/{chequeID}/diagnostic" and request method POST
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants add a diagnostic comment
     * @param diagnostic is data for Diagnostic.class, that was create on client-side
     */
    public void addDiagnostic(@PathVariable Long chequeID, @RequestBody Diagnostic diagnostic) {
        diagnosticRepository.save(
                diagnostic
                        .withDateTime()
                        .withOwner(chequeRepository.findOne(chequeID)));
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

    /**
     * Method addNote call by client-side, when it needs to add note comment to cheque
     * Call with value of request "/cheques/{chequeID}/note" and request method POST
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param note is data for Note.class, that was create on client-side
     */
    public void addNote(@PathVariable Long chequeID, @RequestBody Note note) {
        noteRepository.save(note.withDateTime().withOwner(chequeRepository.findOne(chequeID)));
    }

    /**
     * Method deleteNote call by client-side, when it needs to delete note comment in cheque
     * Call with value of request "/cheques/{chequeID}/note/{noteID}" and request method DELETE
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param noteID is ID of node in database, that client-side wants to delete
     */
    public void deleteNote(@PathVariable Long chequeID, @PathVariable Long noteID) {
        noteRepository.delete(noteID);
    }

    /**
     * Method addCheques call by client-side, when it needs to fill database few sample cheques
     * Call with value of request "/add" and in default request method GET
     * Should send in response OK status, if code works correct
     * @return redirect to main page
     */
    public ModelAndView addSampleCheques() {
        IntStream.range(0, 5).forEach(i -> chequeRepository.save(new Cheque().withRandomData()));
        return new ModelAndView("redirect:/");
    }

    public List<Cheque> attentionCheques() {
        return chequeRepository.findByDiagnosticsIsNull();
    }

    @Transactional(readOnly = true)
    public List<ChequeMin> attentionChequesByDelay() {
        Long[] IDs = chequeRepository.findIdOfChequesWithDelay(OffsetDateTime.now().minusDays(3).toString());
        return chequeRepository.getListOfCompactChequesWithIDs(IDs);
    }

    public void uploadImage(MultipartFile file, Long chequeID, String username) {

        if(!file.isEmpty()) {
            Photo photo = new Photo();
            try {
                photo.setBytes(file.getBytes());
            } catch (IOException e) {e.printStackTrace();}
            photo.setName(file.getOriginalFilename());
            photo.setContentType(file.getContentType());
            photo.setPhotoOwner(chequeRepository.findOne(chequeID));
            photo.setUser(userRepository.findOne(username));
            photo.setAddedDate(OffsetDateTime.now());
            photoRepository.save(photo);
        }
    }

    public Photo getPhotoByID(Long photoID) {return photoRepository.findOne(photoID);}

    public void deletePhotoByID(Long photoID) {photoRepository.delete(photoID);}

    public List<PhotoMin> getListOfCompactPhotoFromCheque(Long chequeID) {
        return photoRepository.getListOfCompactPhotoFromCheque(chequeID);
    }

    public List<Payment> getPaymentsOfCheque(Long chequeID) {
        return paymentRepository.findByPaymentOwnerId(chequeID);
    }

    public void setPaymentsOfCheque(Long chequeID, Set<Payment> payments) {
        Cheque cheque = chequeRepository.findOne(chequeID);
        payments.stream().forEach(payment -> payment.setPaymentOwner(cheque));
        paymentRepository.save(payments);
    }

    public void deletePayment(Long paymentID) {
        paymentRepository.delete(paymentID);
    }

    public List<String> getAutocompleteData(String itemName) {
        System.out.println(itemName);
        return chequeRepository.ListOfCustomers();
    }
}
