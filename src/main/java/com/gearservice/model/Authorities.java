package com.gearservice.model;

import javax.persistence.*;

@Entity
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String authority;

    @ManyToOne
    @JoinColumn(name="username")
    User username;

}
