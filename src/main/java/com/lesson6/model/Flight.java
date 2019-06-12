package com.lesson6.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "FLIGHT")
public class Flight {

    @Id
    @SequenceGenerator(name = "FL_SEQ", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FL_SEQ")
    @Column(name = "ID")
    private Long id;

    @ManyToOne (optional=false, fetch=FetchType.EAGER)
    @JoinColumn (name="PLANE_ID", nullable=false)
    private Plane plane;


    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "FLIGHT_PASSENGER",
            joinColumns = { @JoinColumn(name = "FLIGHT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PASSENGER_ID") }
    )
    private List<Passenger> passengers;


    @Column(name = "DATE_FLIGHT")
    private Date dateFlight;

    @Column(name = "CITY_FROM")
    private String cityFrom;

    @Column(name = "CITY_TO")
    private String cityTo;
}
