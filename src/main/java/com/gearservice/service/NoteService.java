package com.gearservice.service;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.Note;
import com.gearservice.model.repositories.ChequeRepository;
import com.gearservice.model.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class NoteService {

    @Autowired NoteRepository noteRepository;
    @Autowired ChequeRepository chequeRepository;

    /**
     * Method addNote call by client-side, when it needs to add note comment to cheque
     * Call with value of request "/cheques/{chequeID}/note" and request method POST
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param note is data for Note.class, that was create on client-side
     */

    @Modifying
    @Transactional
    public void addNote(@PathVariable Long chequeID, @RequestBody Note note) {
//        Cheque cheque = chequeRepository.findOne(chequeID);
//        cheque.getNotes().add(note.withDateTime());
//        chequeRepository.save(cheque);
        noteRepository.save(note.withDateTime());
    }

    /**
     * Method deleteNote call by client-side, when it needs to delete note comment in cheque
     * Call with value of request "/cheques/{chequeID}/note/{noteID}" and request method DELETE
     * Should send in response OK status, if code works correct
     * @param chequeID is ID of cheque in database, in that client-side wants delete diagnostic comment
     * @param noteID is ID of node in database, that client-side wants to delete
     */

    @Modifying
    @Transactional
    public void deleteNote(@PathVariable Long chequeID, @PathVariable Long noteID) {noteRepository.delete(noteID);}

}
