package com.gearservice.model.cheque;

import javax.persistence.*;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Lob
    private byte[] photo;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public byte[] getPhoto() {return photo;}
    public void setPhoto(byte[] photo) {this.photo = photo;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
