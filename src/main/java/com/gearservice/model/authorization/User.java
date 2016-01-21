package com.gearservice.model.authorization;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class User is model Entity, that store in database and consists user's data.
 * Has many-to-many relationship with Authority entity.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Entity
public class User {

    @Id private String username;
    @JsonIgnore private String password;
    @Transient @JsonProperty private String newPassword;
    @Transient @JsonProperty private String adminPassword;
    private boolean enabled;
    private String fullname;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Set<Authority> authorities = new HashSet<>();

    public User() {}

    public User(User user) {
        super();
        this.username = user.getUsername();
        this.fullname = user.getFullname();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.authorities = user.getAuthorities();
    }

    public User(String username, String newPassword, boolean enabled, String fullname, Set<Authority> authorities) {
        this.username = username;
        this.newPassword = newPassword;
        this.enabled = enabled;
        this.fullname = fullname;
        this.authorities = authorities;
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getNewPassword() {return newPassword;}
    public void setNewPassword(String newPassword) {this.newPassword = newPassword;}
    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) {this.enabled = enabled;}
    public String getFullname() {return fullname;}
    public void setFullname(String fullname) {this.fullname = fullname;}
    public Set<Authority> getAuthorities() {return authorities;}
    public void setAuthorities(Set<Authority> authorities) {this.authorities = authorities;}
    public String getAdminPassword() {return adminPassword;}
    public void setAdminPassword(String adminPassword) {this.adminPassword = adminPassword;}
}
