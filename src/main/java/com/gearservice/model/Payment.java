package com.gearservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_index")
    private Long id;

    private String type;
    private String description;
    private String master;

    private float cost;
    private String currency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cheque", referencedColumnName = "id")
    @JsonBackReference
    private Cheque paymentOwner;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public String getDescroption() {return description;}
    public void setDescroption(String descroption) {this.description = descroption;}
    public String getMaster() {return master;}
    public void setMaster(String master) {this.master = master;}
    public float getCost() {return cost;}
    public void setCost(float cost) {this.cost = cost;}
    public String getCurrency() {return currency;}
    public void setCurrency(String currency) {this.currency = currency;}
    public Cheque getPaymentOwner() {return paymentOwner;}
    public void setPaymentOwner(Cheque paymentOwner) {this.paymentOwner = paymentOwner;}
}
