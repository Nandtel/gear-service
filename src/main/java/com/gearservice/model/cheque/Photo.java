package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Model class for Photo mongo's entity.
 * Store data and bytes of photo in mongoDB.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */
@Document
public class Photo {

    @Id
    private String id;

    private String name;
    private String contentType;

    private String chequeId;
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime addDate;

    private byte[] bytes;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public byte[] getBytes() {return bytes;}
    public void setBytes(byte[] bytes) {this.bytes = bytes;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getContentType() {return contentType;}
    public void setContentType(String contentType) {this.contentType = contentType;}
    public String getChequeId() {return chequeId;}
    public void setChequeId(String chequeId) {this.chequeId = chequeId;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public OffsetDateTime getAddDate() {return addDate;}
    public void setAddDate(OffsetDateTime addDate) {this.addDate = addDate;}
}
