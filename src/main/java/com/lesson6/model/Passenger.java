package com.lesson6.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

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


    @Transient
    @ManyToMany
    @JoinTable(
        name = "FLIGHT_PASSENGER",
        joinColumns = { @JoinColumn(name = "PASSENGER_ID", referencedColumnName = "ID") },
        inverseJoinColumns = { @JoinColumn(name = "FLIGHT_ID", referencedColumnName = "ID") }
    )
    private Set<Flight> flights = new HashSet<>();

}
