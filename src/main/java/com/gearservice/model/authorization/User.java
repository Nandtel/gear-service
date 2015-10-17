package com.gearservice.model.authorization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id private String username;
    @JsonIgnore private String password;
    @Transient private String newPassword;
    private boolean enabled;
    private String fullName;

    @OneToMany(mappedBy = "username", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Authorities> authorities;

    public User() {}

    public User(User user) {
        super();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.authorities = user.getAuthorities();
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getNewPassword() {return newPassword;}
    public void setNewPassword(String newPassword) {this.newPassword = newPassword;}
    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) {this.enabled = enabled;}
    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

    public Set<Authorities> getAuthorities() {return authorities;}
    public void setAuthorities(Set<Authorities> authorities) {this.authorities = authorities;}
}
