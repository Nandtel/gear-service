package com.gearservice.model.authorization;

import com.fasterxml.jackson.annotation.*;
import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.Diagnostic;
import com.gearservice.model.cheque.Payment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id private String username;
    @JsonIgnore private String password;
    @Transient private String newPassword;
    private boolean enabled;
    private String fullname;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "engineer", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Cheque> chequesAsEngineer;

    @OneToMany(mappedBy = "secretary", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Cheque> chequesAsSecretary;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Diagnostic> diagnosticsAsEngineer;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Payment> payments;

    public User() {}

    public User(User user) {
        super();
        this.username = user.getUsername();
        this.fullname = user.getFullname();
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
    public String getFullname() {return fullname;}
    public void setFullname(String fullname) {this.fullname = fullname;}
    public Set<Authority> getAuthorities() {return authorities;}
    public void setAuthorities(Set<Authority> authorities) {this.authorities = authorities;}
    public Set<Cheque> getChequesAsEngineer() {return chequesAsEngineer;}
    public void setChequesAsEngineer(Set<Cheque> chequesAsEngineer) {this.chequesAsEngineer = chequesAsEngineer;}
    public Set<Cheque> getChequesAsSecretary() {return chequesAsSecretary;}
    public void setChequesAsSecretary(Set<Cheque> chequesAsSecretary) {this.chequesAsSecretary = chequesAsSecretary;}
    public Set<Diagnostic> getDiagnosticsAsEngineer() {return diagnosticsAsEngineer;}
    public void setDiagnosticsAsEngineer(Set<Diagnostic> diagnosticsAsEngineer) {this.diagnosticsAsEngineer = diagnosticsAsEngineer;}
    public Set<Payment> getPayments() {return payments;}
    public void setPayments(Set<Payment> payments) {this.payments = payments;}
}
