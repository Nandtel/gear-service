package com.gearservice.model;

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
public class Kit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kit_index")
    private Long id;

    @Column(name = "kit_text")
    @Lob
    private String text;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cheque", referencedColumnName = "id")
    @JsonBackReference
    private Cheque kitOwner;

    public Kit() {}

    /**
     * Method withRandomData handle current Kit object filling it with random data and return this edited object
     * @return this Kit object after editing
     */
    public Kit withRandomData() {
        this.setText(SampleDataService.getRandomKit());
        return this;
    }

    /**
     * Method withOwner handle current Kit object filling it with owner and return this edited object
     * @param owner is owner class in bidirectional one-to-many relationship
     * @return this Kit object after editing
     */
    public Kit withOwner(Cheque owner) {
        this.setKitOwner(owner);
        return this;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public void setText(String text) {this.text = text;}
    public String getText() {return text;}
    public void setKitOwner(Cheque kitOwner) {this.kitOwner = kitOwner;}
    public Cheque getKitOwner() {return kitOwner;}

}
