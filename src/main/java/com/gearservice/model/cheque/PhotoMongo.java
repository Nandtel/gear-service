package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.gearservice.config.converter.OffsetDateTimePersistenceConverter;

import javax.persistence.*;
import java.time.OffsetDateTime;

public class PhotoMongo {

    @Id
    private String id;

    private String name;
    private String contentType;

    private String photoOwner;
    private String user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime addedDate;

    private byte[] bytes;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public byte[] getBytes() {return bytes;}
    public void setBytes(byte[] bytes) {this.bytes = bytes;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getContentType() {return contentType;}
    public void setContentType(String contentType) {this.contentType = contentType;}
    public String getPhotoOwner() {return photoOwner;}
    public void setPhotoOwner(String photoOwner) {this.photoOwner = photoOwner;}
    public String getUser() {return user;}
    public void setUser(String user) {this.user = user;}
    public OffsetDateTime getAddedDate() {return addedDate;}
    public void setAddedDate(OffsetDateTime addedDate) {this.addedDate = addedDate;}
}
