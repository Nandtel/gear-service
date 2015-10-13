package com.gearservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Authorities implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String authority;

    @ManyToOne
    @JoinColumn(name="username")
    @JsonBackReference
    private User username;

    public void setId(long id) {this.id = id;}
    public User getUsername() {return username;}
    public void setUsername(User username) {this.username = username;}
    public void setAuthority(String authority) {this.authority = authority;}
    @Override public String getAuthority() {return authority;}
}
