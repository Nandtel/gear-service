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

    /**
     * Method addNote call by client-side, when it needs to add new note comment to cheque
     * Call with value of request "/api/cheques/{chequeID}/notes" and request method POST
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants add a note comment
     * @param note is data for Note.class, that was create on client-side
     * @param principal is user, that request this action
     */
    @RequestMapping(value = "/api/cheques/{chequeID}/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addNote(@PathVariable Long chequeID, @RequestBody Note note, Principal principal) {
        noteService.addNote(chequeID, note);
        logger.info("User " + principal.getName().toUpperCase() + " has added note to cheque №" + chequeID);
    }

    /**
     * Method deleteNote call by client-side, when it needs to delete note comment in cheque
     * Call with value of request "/api/cheques/{chequeID}/notes/{noteID}" and request method DELETE
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants add a note comment
     * @param noteID is ID of note in database
     * @param principal is user, that request this action
     */
    @RequestMapping(value = "/api/cheques/{chequeID}/notes/{noteID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteNote(@PathVariable Long chequeID, @PathVariable Long noteID, Principal principal) {
        noteService.deleteNote(chequeID, noteID);
        logger.info("User " + principal.getName().toUpperCase() + " has removed note from cheque №" + chequeID);
    }

}
