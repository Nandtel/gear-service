package com.gearservice.service;

import com.gearservice.model.cheque.Note;
import com.gearservice.repositories.jpa.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class NoteService is service, that handle NoteController
 * Use @Autowired for connect to necessary repositories
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Method addNote add note to DB with current DateTime
     * @param note is data for Note.class, that was create on client-side
     */
    @Modifying
    @Transactional
    public void addNote(Note note) {
        noteRepository.save(note.withDateTime());
    }

    /**
     * Method deleteNote delete note from DB
     * @param chequeID is ID of cheque in database, in that client-side wants delete note comment
     * @param noteID is ID of note in database, that client-side wants to delete
     */
    @Modifying
    @Transactional
    public void deleteNote(Long chequeID, Long noteID) {noteRepository.deleteById(noteID);}

    @Transactional(readOnly = true)
    public List<Note> getNotes(Long chequeID) {
        return noteRepository.findByChequeId(chequeID);
    }

}
