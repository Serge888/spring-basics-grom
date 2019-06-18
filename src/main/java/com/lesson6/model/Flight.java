package com.lesson6.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@ToString(exclude = "passengers")
@EqualsAndHashCode
@Entity
@Table(name = "FLIGHT")
public class Flight {
    private Long id;
    private Plane plane;
    private List<Passenger> passengers = new ArrayList<>();
    private Date dateFlight;
    private String cityFrom;
    private String cityTo;



    @Id
    @SequenceGenerator(name = "FL_SEQ", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FL_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne (optional=false, fetch=FetchType.EAGER)
    @JoinColumn (name="PLANE_ID", nullable=false)
    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "FLIGHT_PASSENGER",
            joinColumns = { @JoinColumn(name = "FLIGHT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PASSENGER_ID") }
    )
    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Column(name = "DATE_FLIGHT")
    public Date getDateFlight() {
        return dateFlight;
    }

    public void setDateFlight(Date dateFlight) {
        this.dateFlight = dateFlight;
    }

    @Column(name = "CITY_FROM")
    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    @Column(name = "CITY_TO")
    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

}
