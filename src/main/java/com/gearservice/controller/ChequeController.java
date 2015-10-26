package com.gearservice.controller;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.ChequeMin;
import com.gearservice.model.cheque.Diagnostic;
import com.gearservice.model.cheque.Note;
import com.gearservice.service.ChequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @RequestMapping(value = "/attention", method = RequestMethod.GET)
    public List<Cheque> attentionCheques() {
        return chequeService.attentionCheques();
    }

    @RequestMapping(value = "/delay", method = RequestMethod.GET)
    public List<ChequeMin> attentionChequesByDelay() {
        return chequeService.attentionChequesByDelay();
    }

    @RequestMapping(value = "/upload-image", method = RequestMethod.POST)
    public String uploadImage (@RequestParam("file") MultipartFile image) {

        if(!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(image.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded";
            } catch (Exception e) {
                return "You failed to upload => " + e.getMessage();
            }
        } else
            return "You failed to upload because the file was empty.";

    }

}
