package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gearservice.service.SampleDataService;

import javax.persistence.*;

/**
 * Class Kit is model Entity, that store in database and consists kit data.
 * It is affiliated class in many-to-one bidirectional relationship with Cheque owner.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cheque_id", referencedColumnName = "id")
    @JsonBackReference
    private Cheque cheque;

    public Component() {}

    /**
     * Method withRandomData handle current Kit object filling it with random data and return this edited object
     * @return this Kit object after editing
     */
    public Component withRandomData() {
        this.setName(SampleDataService.getRandomKit());
        return this;
    }

    /**
     * Method withCheque handle current Kit object filling it with owner and return this edited object
     * @param owner is owner class in bidirectional one-to-many relationship
     * @return this Kit object after editing
     */
    public Component withOwner(Cheque owner) {
        this.setCheque(owner);
        return this;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
    public void setCheque(Cheque cheque) {this.cheque = cheque;}
    public Cheque getCheque() {return cheque;}

}
