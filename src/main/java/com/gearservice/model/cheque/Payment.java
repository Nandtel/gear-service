package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gearservice.model.authorization.User;
import com.gearservice.model.exchangeRate.ExchangeRate;

import javax.persistence.*;

/**
 * Model class for Payment entity.
 * Store discrete payment in balance entity.
 * Has relations with parent entity - balance, with exchange_rate entity and with creator.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String description;

    private float cost;
    private String currency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    @JsonBackReference
    private Balance balance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exchange_rate_add_date", referencedColumnName = "addDate")
    private ExchangeRate exchangeRate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public float getCost() {return cost;}
    public void setCost(float cost) {this.cost = cost;}
    public Balance getBalance() {return balance;}
    public void setBalance(Balance balance) {this.balance = balance;}
    public String getCurrency() {return currency;}
    public void setCurrency(String currency) {this.currency = currency;}
    public ExchangeRate getExchangeRate() {return exchangeRate;}
    public void setExchangeRate(ExchangeRate exchangeRate) {this.exchangeRate = exchangeRate;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
