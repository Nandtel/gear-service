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
 * Class Diagnostic is model Entity, that store in database and consists diagnostic data.
 * It is affiliated class in many-to-one bidirectional relationship with Cheque owner.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@Entity
public class Diagnostic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime date;

    @Lob
    private String text;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "cheque_id", referencedColumnName = "id")
//    @JsonBackReference
//    private Cheque cheque;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

    /**
     * Method withRandomData handle current Diagnostic object filling it with random data and return this edited object
     * @return this Diagnostic object after editing
     */
    public Diagnostic withRandomData() {
        this.setText(SampleDataService.getRandomComment());
        this.setDate(SampleDataService.getRandomDate());
        return this;
    }

    /**
     * Method withDateTimeAndUser handle current Diagnostic object filling it fields: current date to date and
     * name to user, then return this edited object
     * @return this Diagnostic object after editing
     */
    public Diagnostic withDateTime() {
        this.setDate(OffsetDateTime.now());
        return this;
    }

    public Diagnostic withUser(User user) {
        this.user = user;
        return this;
    }

    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}
    public void setDate(OffsetDateTime date) {this.date = date;}
    public OffsetDateTime getDate() {return date;}
    public void setText(String text) {this.text = text;}
    public String getText() {return text;}
//    public void setCheque(Cheque cheque) {this.cheque = cheque;}
//    public Cheque getCheque() {return cheque;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
