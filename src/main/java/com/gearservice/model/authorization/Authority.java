package com.gearservice.model.authorization;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Authority implements GrantedAuthority {

    @Id
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    public Authority() {}

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority withUsername(User username) {
        this.users.add(username);
        return this;
    }

    public Set<User> getUsers() {return users;}
    public void setUsers(Set<User> username) {this.users = username;}
    public void setAuthority(String authority) {this.authority = authority;}
    @Override public String getAuthority() {return authority;}
}
