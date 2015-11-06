package com.gearservice.controller;

import com.gearservice.model.cheque.*;
import com.gearservice.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ChequeController {

    @Autowired ChequeService chequeService;

    @RequestMapping(value = "/api/cheques", method = RequestMethod.GET)
    public List<ChequeMin> getMinChequesList() {
        return chequeService.getMinChequesList();
    }

    @RequestMapping(value = "/api/cheques", method = RequestMethod.POST)
    public Cheque synchronizeCheque(@RequestBody Cheque cheque) {
        return chequeService.synchronizeCheque(cheque);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}", method = RequestMethod.GET)
    public Cheque getCheque(@PathVariable Long chequeID) {
        return chequeService.getCheque(chequeID);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCheque(@PathVariable Long chequeID) {
        chequeService.deleteCheque(chequeID);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}/diagnostics", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addDiagnostic(@PathVariable Long chequeID, @RequestBody Diagnostic diagnostic) {
        chequeService.addDiagnostic(chequeID, diagnostic);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}/diagnostics/{diagnosticID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteDiagnostic(@PathVariable Long chequeID, @PathVariable Long diagnosticID) {
        chequeService.deleteDiagnostic(chequeID, diagnosticID);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addNote(@PathVariable Long chequeID, @RequestBody Note note) {
        chequeService.addNote(chequeID, note);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}/notes/{noteID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteNote(@PathVariable Long chequeID, @PathVariable Long noteID) {
        chequeService.deleteNote(chequeID, noteID);
    }

    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    public ModelAndView addSampleCheques() {
        chequeService.addSampleCheques();
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/api/attention", method = RequestMethod.GET)
    public List<Cheque> attentionCheques() {return chequeService.attentionCheques();}

    @RequestMapping(value = "/api/delay", method = RequestMethod.GET)
    public List<ChequeMin> attentionChequesByDelay() {return chequeService.attentionChequesByDelay();}

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/api/upload-image", method = RequestMethod.POST)
    public void uploadImage(@RequestParam("file") MultipartFile photo,
                            @RequestParam("chequeID") Long chequeID,
                            @RequestParam("username") String username) {
        chequeService.uploadImage(photo, chequeID, username);
    }

    @RequestMapping(value = "/api/photo/{photoID}", method = RequestMethod.GET)
    public Photo getPhotoByID(@PathVariable Long photoID) {return chequeService.getPhotoByID(photoID);}

    @RequestMapping(value = "/api/photo/{photoID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePhotoByID(@PathVariable Long photoID) {chequeService.deletePhotoByID(photoID);}

    @RequestMapping(value = "/api/photo/cheque/{chequeID}", method = RequestMethod.GET)
    public List<PhotoMin> getListOfCompactPhotoFromCheque(@PathVariable Long chequeID) {
        return chequeService.getListOfCompactPhotoFromCheque(chequeID);
    }
}
