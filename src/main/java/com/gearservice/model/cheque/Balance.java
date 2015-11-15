package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@NamedEntityGraph(name = "balance.full", attributeNodes = {
        @NamedAttributeNode("payments")
})
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int estimatedCost;
    private boolean paidStatus;

    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cheque_id")
    private Cheque cheque;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public int getEstimatedCost() {return estimatedCost;}
    public void setEstimatedCost(int estimatedCost) {this.estimatedCost = estimatedCost;}
    public boolean getPaidStatus() {return paidStatus;}
    public void setPaidStatus(boolean paidStatus) {this.paidStatus = paidStatus;}
    public Set<Payment> getPayments() {return payments;}
    public void setPayments(Set<Payment> payments) {this.payments = payments;}
    public Cheque getCheque() {return cheque;}
    public void setCheque(Cheque cheque) {this.cheque = cheque;}
}
