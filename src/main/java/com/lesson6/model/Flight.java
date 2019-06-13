package com.lesson6.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "FLIGHT")
public class Flight {

    @Id
    @SequenceGenerator(name = "FL_SEQ", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FL_SEQ")
    @Column(name = "ID")
    private Long id;

    @ManyToOne (optional=false, fetch=FetchType.LAZY)
    @JoinColumn (name="PLANE_ID", nullable=false)
    private Plane plane;

//    @Transient
    @ManyToMany
    @JoinTable(
            name = "FLIGHT_PASSENGER",
            joinColumns = { @JoinColumn(name = "FLIGHT_ID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "PASSENGER_ID", referencedColumnName = "ID") }
    )
    private Set<Passenger> passengers = new HashSet<>();


    @Column(name = "DATE_FLIGHT")
    private Date dateFlight;

    @Column(name = "CITY_FROM")
    private String cityFrom;

    @Column(name = "CITY_TO")
    private String cityTo;
}
