package com.gearservice.controller;

import com.gearservice.model.cheque.Note;
import com.gearservice.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired NoteService noteService;

    @RequestMapping(value = "/api/cheques/{chequeID}/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addNote(@PathVariable Long chequeID, @RequestBody Note note, Principal principal) {
        noteService.addNote(chequeID, note);
        logger.info("User " + principal.getName().toUpperCase() + " has added note to cheque №" + chequeID);
    }

    @RequestMapping(value = "/api/cheques/{chequeID}/notes/{noteID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteNote(@PathVariable Long chequeID, @PathVariable Long noteID, Principal principal) {
        noteService.deleteNote(chequeID, noteID);
        logger.info("User " + principal.getName().toUpperCase() + " has removed note from cheque №" + chequeID);
    }

}
