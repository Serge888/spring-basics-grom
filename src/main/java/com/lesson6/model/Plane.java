package com.lesson6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PLANE")
@EqualsAndHashCode
@NamedQuery(name = "Plane.OldPlanes", query = "select p from Plane p where p.yearProduced < :date")
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
