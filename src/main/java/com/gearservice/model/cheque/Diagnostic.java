package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.gearservice.config.converter.OffsetDateTimePersistenceConverter;
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
    private OffsetDateTime time;

    private String user;

    @Lob
    private String text;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cheque", referencedColumnName = "id")
    @JsonBackReference
    private Cheque diagnosticOwner;

    /**
     * Method withRandomData handle current Diagnostic object filling it with random data and return this edited object
     * @return this Diagnostic object after editing
     */
    public Diagnostic withRandomData() {
        this.setText(SampleDataService.getRandomComment());
        this.setTime(SampleDataService.getRandomDate());
        this.setUser(SampleDataService.getRandomName());
        return this;
    }

    /**
     * Method withDateTimeAndUser handle current Diagnostic object filling it fields: current time to time and
     * name to user, then return this edited object
     * @return this Diagnostic object after editing
     */
    public Diagnostic withDateTimeAndUser() {
        this.setTime(OffsetDateTime.now());
        this.setUser("Администратор");
        return this;
    }

    /**
     * Method withOwner handle current Diagnostic object filling it with owner and return this edited object
     * @param owner is owner class in bidirectional one-to-many relationship
     * @return this Diagnostic object after editing
     */
    public Diagnostic withOwner(Cheque owner) {
        this.setDiagnosticOwner(owner);
        return this;
    }

    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}
    public void setTime(OffsetDateTime time) {this.time = time;}
    public OffsetDateTime getTime() {return time;}
    public void setUser(String user) {this.user = user;}
    public String getUser() {return user;}
    public void setText(String text) {this.text = text;}
    public String getText() {return text;}
    public void setDiagnosticOwner(Cheque diagnosticOwner) {this.diagnosticOwner = diagnosticOwner;}
    public Cheque getDiagnosticOwner() {return diagnosticOwner;}

}
