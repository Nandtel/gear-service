package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.gearservice.config.converter.OffsetDateTimePersistenceConverter;
import com.gearservice.model.authorization.User;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class PhotoMin {

    @Id
    private Long id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime addedDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cheque", referencedColumnName = "id")
    private Cheque photoOwner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User user;

    public PhotoMin() {}

    public PhotoMin(Long id, String name, OffsetDateTime addedDate, Cheque photoOwner, User user) {
        this.id = id;
        this.name = name;
        this.addedDate = addedDate;
        this.photoOwner = photoOwner;
        this.user = user;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public OffsetDateTime getAddedDate() {return addedDate;}
    public void setAddedDate(OffsetDateTime addedDate) {this.addedDate = addedDate;}
    public Cheque getPhotoOwner() {return photoOwner;}
    public void setPhotoOwner(Cheque photoOwner) {this.photoOwner = photoOwner;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
