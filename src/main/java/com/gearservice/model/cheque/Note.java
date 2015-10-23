package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.gearservice.config.converter.OffsetDateTimePersistenceConverter;
import com.gearservice.model.authorization.User;
import com.gearservice.service.SampleDataService;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Class Note is model Entity, that store in database and consists note data.
 * It is affiliated class in many-to-one bidirectional relationship with Cheque owner.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime time;

    @Lob
    private String text;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cheque", referencedColumnName = "id")
    @JsonBackReference
    private Cheque noteOwner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User user;

    /**
     * Method withRandomData handle current Note object filling it with random data and return this edited object
     * @return this Note object after editing
     */
    public Note withRandomData() {
        this.setText(SampleDataService.getRandomComment());
        this.setTime(SampleDataService.getRandomDate());
        return this;
    }

    /**
     * Method withDateTimeAndUser handle current Note object filling it fields: current time to time and
     * name to user, then return this edited object
     * @return this Note object after editing
     */
    public Note withDateTime() {
        this.setTime(OffsetDateTime.now());
        return this;
    }

    public Note withUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * Method withOwner handle current Note object filling it with owner and return this edited object
     * @param owner is owner class in bidirectional one-to-many relationship
     * @return this Note object after editing
     */
    public Note withOwner(Cheque owner) {
        this.setNoteOwner(owner);
        return this;
    }

    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}
    public void setText(String text) {this.text = text;}
    public String getText() {return text;}
    public void setTime(OffsetDateTime time) {this.time = time;}
    public OffsetDateTime getTime() {return time;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public void setNoteOwner(Cheque noteOwner) {this.noteOwner = noteOwner;}
    public Cheque getNoteOwner() {return noteOwner;}
}
