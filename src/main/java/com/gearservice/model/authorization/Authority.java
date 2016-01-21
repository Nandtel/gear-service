package com.gearservice.model.authorization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Authority is model Entity, that store in database and consists authority of user data.
 * Has many-to-many relationship with User entity.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Entity
public class Authority implements GrantedAuthority {

    @Id
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Authority() {}

    public Authority(String authority) {this.authority = authority;}

    /**
     * Method withUsername handle current Authority entity filling it with username and return this edited object
     * @param username is User's entity in many-to-many relationship
     * @return this Authority object after editing
     */
    public Authority withUsername(User username) {
        this.users.add(username);
        return this;
    }

    public Set<User> getUsers() {return users;}
    public void setUsers(Set<User> username) {this.users = username;}
    public void setAuthority(String authority) {this.authority = authority;}
    @Override public String getAuthority() {return authority;}
}
