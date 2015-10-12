package com.gearservice.controller;

import com.gearservice.model.*;
import com.gearservice.model.Currency;
import com.gearservice.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Class Application is controller, that handles all request from client-side
 * Use @Autowired for connect to necessary repositories and Entity manager
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@RestController
public class Application {

    /**
     * Connect to cheque repository from model/repositories
     */
    @Autowired
    ChequeRepository chequeRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    /**
     * Connect to diagnostic repository from model/repositories
     */
    @Autowired
    DiagnosticRepository diagnosticRepository;

    /**
     * Connect to note repository from model/repositories
     */
    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Connect to entity manager
     */
    @Autowired
    EntityManager em;

    /**
     * Method index call by client-side and return index page
     * @return index.html from templates
     */
    @RequestMapping("/")        public ModelAndView index() {return new ModelAndView("index");}

    /**
     * Method getCheques call by client-side and return all cheques from database
     * Native query use for create partial object of Cheque — ChequeMin, that has only necessary for client-side fields
     * Call with value "/cheques" and request method GET
     * @return list of all cheques, that database has
     */
    @RequestMapping(value = "/cheques", method = RequestMethod.GET)
    public List<ChequeMin> getCheques() {
        return (List<ChequeMin>) em.createNativeQuery("SELECT id, name_of_customer, introduced_date, name_of_product, " +
                "model, serial_number, purchaser_name, inspector_name, master_name, guarantee_date, ready_date, " +
                "issued_date, has_paid_status, has_guarantee_status, has_ready_status, has_issued_status " +
                "FROM cheque", ChequeMin.class).getResultList();
    }

    /**
     * Method saveCheque call by client-side with data for cheque.class
     * Call with value "/cheque" and request method POST
     * @param cheque is data for Cheque.class, that was create on client-side
     * @return Cheque, that added
     */
    @RequestMapping(value = "/cheques", method = RequestMethod.POST)
    public Cheque saveCheque(@RequestBody Cheque cheque) {
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
    @RequestMapping(value = "/cheques/{chequeID}", method = RequestMethod.GET)
    public Cheque getCheque(@PathVariable Long chequeID) {
        return chequeRepository.findOne(chequeID);
    }

    /**
     * Method deleteCheque call by client-side, when it needs to delete one cheque
     * Call with value of request "/cheques/{chequeID}" and request method DELETE
     * @param chequeID is ID of cheque in database, that client-side wants to delete
     * @return redirect to main page
     */
    @RequestMapping(value = "/cheques/{chequeID}", method = RequestMethod.DELETE)
    public ModelAndView deleteCheque(@PathVariable Long chequeID) {
        chequeRepository.delete(chequeID);
        return new ModelAndView("redirect:/");
    }

    /**
     * Method addDiagnostic call by client-side, when it needs to add new diagnostic comment to cheque
     * Call with value of request "/cheques/{chequeID}/diagnostic" and request method POST
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants add a diagnostic comment
     * @param diagnostic is data for Diagnostic.class, that was create on client-side
     */
    @RequestMapping(value = "/cheques/{chequeID}/diagnostics", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addDiagnostic(@PathVariable Long chequeID, @RequestBody Diagnostic diagnostic) {
        diagnosticRepository.save(diagnostic.withDateTimeAndUser().withOwner(chequeRepository.findOne(chequeID)));
    }

    /**
     * Method deleteDiagnostic call by client-side, when it needs to delete diagnostic comment in cheque
     * Call with value of request "/cheques/{chequeID}/diagnostic/{diagnosticID}" and request method DELETE
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param diagnosticID is ID of diagnostic in database, that client-side wants to delete
     */
    @RequestMapping(value = "/cheques/{chequeID}/diagnostics/{diagnosticID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteDiagnostic(@PathVariable Long chequeID, @PathVariable Long diagnosticID) {
        Cheque cheque = chequeRepository.findOne(chequeID);
        cheque.getDiagnostics().removeIf(diagnostic -> Objects.equals(diagnostic.getId(), diagnosticID));
        chequeRepository.save(cheque);
    }

    /**
     * Method addNote call by client-side, when it needs to add note comment to cheque
     * Call with value of request "/cheques/{chequeID}/note" and request method POST
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param note is data for Note.class, that was create on client-side
     */
    @RequestMapping(value = "/cheques/{chequeID}/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addNote(@PathVariable Long chequeID, @RequestBody Note note) {
        noteRepository.save(note.withDateTimeAndUser().withOwner(chequeRepository.findOne(chequeID)));
    }

    /**
     * Method deleteNote call by client-side, when it needs to delete note comment in cheque
     * Call with value of request "/cheques/{chequeID}/note/{noteID}" and request method DELETE
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param noteID is ID of node in database, that client-side wants to delete
     */
    @RequestMapping(value = "/cheques/{chequeID}/notes/{noteID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteNote(@PathVariable Long chequeID, @PathVariable Long noteID) {
        Cheque cheque = chequeRepository.findOne(chequeID);
        cheque.getNotes().removeIf(note -> Objects.equals(note.getId(), noteID));
        chequeRepository.save(cheque);
    }

    /**
     * Method addCheques call by client-side, when it needs to fill database few sample cheques
     * Call with value of request "/add" and in default request method GET
     * Should send in response OK status, if code works correct
     * @return redirect to main page
     */
    @RequestMapping("/sample")
    public ModelAndView addCheques() {
        IntStream.range(0, 5).forEach(i -> chequeRepository.save(new Cheque().withRandomData()));
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/currency-rate", method = RequestMethod.GET)
    public Currency getCurrencyRates() {
        Long now = LocalDate.now().toEpochDay();

        if(!currencyRepository.exists(now))
            currencyRepository.save(new Currency()
                    .forToday()
                    .withRUB()
                    .getFromServer("http://minfindnr.ru/", "li#text-12 font"));

        return currencyRepository.findOne(now);
    }

    @RequestMapping(value = "/currency-rate-clean", method = RequestMethod.GET)
    public Currency getCleanCurrencyRates() {
        Long now = LocalDate.now().toEpochDay();

        currencyRepository.save(new Currency()
                    .forToday()
                    .withRUB()
                    .getFromServer("http://minfindnr.ru/", "li#text-12 font"));

        return currencyRepository.findOne(now);
    }

    @RequestMapping(value = "/currency-rate-list", method = RequestMethod.GET)
    public List<Currency> getCur() {
        return currencyRepository.findAll();
    }

    @RequestMapping(value = "/currency-rate-list", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void setCur(@RequestBody List<Currency> list) {
        currencyRepository.save(list);
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }
}
