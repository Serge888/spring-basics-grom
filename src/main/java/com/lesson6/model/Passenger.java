package com.lesson6.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "PASSENGER")
public class Passenger {

    @Id
    @SequenceGenerator(name = "PS_SEQ", sequenceName = "PASSENGER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PS_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "PASSPORT_CODE")
    private String passportCode;

    @ManyToMany(mappedBy = "passengers")
    private List<Flight> flights;

}
