package com.gearservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    public String username;

    public String password;
    public boolean enabled;

    @OneToMany
    public List<Authorities> authorities;

}
