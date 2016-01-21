package com.gearservice.model.cheque;

import com.gearservice.service.SampleDataService;

import javax.persistence.*;

/**
 * Class Component is model Entity, that store in database and consists Component data.
 * It is affiliated class in many-to-one bidirectional relationship with Cheque owner.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Component(String name) {this.name = name;}

    public Component() {}

    /**
     * Method withRandomData handle current Kit object filling it with random data and return this edited object
     * @return this Kit object after editing
     */
    public Component withRandomData() {
        this.setName(SampleDataService.getRandomKit());
        return this;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public String getName() {return name;}

}
