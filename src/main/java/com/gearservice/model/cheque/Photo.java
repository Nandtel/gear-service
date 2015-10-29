package com.gearservice.model.cheque;

import javax.persistence.*;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String contentType;

    @Lob
    private byte[] photo;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public byte[] getPhoto() {return photo;}
    public void setPhoto(byte[] photo) {this.photo = photo;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getContentType() {return contentType;}
    public void setContentType(String contentType) {this.contentType = contentType;}
}
