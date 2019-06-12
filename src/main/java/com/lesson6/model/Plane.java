package com.lesson6.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PLANE")
@EqualsAndHashCode
public class Plane {

    @Id
    @SequenceGenerator(name = "PE_SEQ", sequenceName = "PLANE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PE_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "CODE")
    private String code;

    @Column(name = "YEAR_PRODUCED")
    private Date yearProduced;

    @Column(name = "AVG_FUEL_CONSUMPTION")
    private Double avgFuelConsumption;

}
