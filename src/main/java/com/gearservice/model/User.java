package com.gearservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id private String username;
    @JsonIgnore private String password;
    private boolean enabled;
    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "username", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Authorities> authorities;

    public User() {}

    public User(User user) {
        super();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.authorities = user.getAuthorities();
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) {this.enabled = enabled;}
    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}
    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}
    public Set<Authorities> getAuthorities() {return authorities;}
    public void setAuthorities(Set<Authorities> authorities) {this.authorities = authorities;}


}
