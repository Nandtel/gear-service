package com.gearservice.model.cheque;

import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;
import java.util.Set;

/**
 * Model class for Balance entity.
 * Has generated id, version field for hibernate optimistic locking,
 * child's payments entities, that not excluded from optimistic's locking handling
 * and relation with cheque - parent entity
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Entity
@NamedEntityGraph(name = "balance.full", attributeNodes = {
        @NamedAttributeNode("payments")
})
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    private int estimatedCost;
    private boolean paidStatus;

    @OptimisticLock(excluded = false)
    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cheque_id")
    private Cheque cheque;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Long getVersion() {return version;}
    public void setVersion(Long version) {this.version = version;}
    public int getEstimatedCost() {return estimatedCost;}
    public void setEstimatedCost(int estimatedCost) {this.estimatedCost = estimatedCost;}
    public boolean getPaidStatus() {return paidStatus;}
    public void setPaidStatus(boolean paidStatus) {this.paidStatus = paidStatus;}
    public Set<Payment> getPayments() {return payments;}
    public void setPayments(Set<Payment> payments) {this.payments = payments;}
    public Cheque getCheque() {return cheque;}
    public void setCheque(Cheque cheque) {this.cheque = cheque;}
}
