package com.gearservice.controller;

import com.gearservice.model.cheque.Note;
import com.gearservice.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Class NoteController is controller, that handle requests of notes.
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

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
