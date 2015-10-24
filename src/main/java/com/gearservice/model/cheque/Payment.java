package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gearservice.model.authorization.User;
import com.gearservice.model.currency.Currency;

import javax.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_index")
    private Long id;

    private String type;
    private String description;

    private float cost;
    private String currentCurrency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cheque", referencedColumnName = "id")
    @JsonBackReference
    private Cheque paymentOwner;

    @ManyToOne
    Currency currency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User user;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public float getCost() {return cost;}
    public void setCost(float cost) {this.cost = cost;}
    public Cheque getPaymentOwner() {return paymentOwner;}
    public void setPaymentOwner(Cheque paymentOwner) {this.paymentOwner = paymentOwner;}
    public String getCurrentCurrency() {return currentCurrency;}
    public void setCurrentCurrency(String currentCurrency) {this.currentCurrency = currentCurrency;}
    public Currency getCurrency() {return currency;}
    public void setCurrency(Currency currency) {this.currency = currency;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
