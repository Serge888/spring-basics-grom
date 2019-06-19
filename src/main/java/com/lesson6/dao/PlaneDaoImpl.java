package com.lesson6.dao;

import com.lesson6.model.Flight;
import com.lesson6.model.Plane;
import com.lesson6.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Transactional
@Repository
public class PlaneDaoImpl implements PlaneDao {
    @Value("${yearsForOldPlanes:20}")
    private Integer yearsForOldPlanes;
    @Value("${flightsForRegularPlanes:300}")
    private Integer flightsForRegularPlanes;
    private FlightDao flightDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PlaneDaoImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }


    @Override
    public Plane save(Plane plane) {
        entityManager.persist(plane);
        return plane;
    }

    @Override
    public Plane update(Plane plane) {
        entityManager.merge(plane);
        return plane;
    }

    @Override
    public Plane delete(Long id) {
        Plane plane = entityManager.find(Plane.class, id);
        entityManager.remove(plane);
        return plane;
    }

    @Override
    public Plane findById(Long id) {
        return entityManager.find(Plane.class, id);

    }

    @Override
    public List<Plane> oldPlanes() {
        LocalDate twentyYearsEarlier = LocalDate.now().minusYears(yearsForOldPlanes);
        Date dateOld = DateUtil.localDateToDate(twentyYearsEarlier);
        return entityManager.createNamedQuery("Plane.OldPlanes", Plane.class)
                .setParameter("date", dateOld)
                .getResultList();
    }


    @Override
    public List<Plane> regularPlanes(int year) {
        List<Flight> flightList = flightDao.flightsForYear(year);
        Map<Plane, Integer> planeMap = new HashMap<>();
        Plane plane;
        for (Flight flight : flightList) {
            plane = flight.getPlane();
            if (!planeMap.containsKey(plane)) {
                planeMap.put(plane, 1);
            } else {
                planeMap.put(plane, planeMap.get(plane) + 1);
            }
        }
        List<Plane> planeList = new ArrayList<>();
        planeMap.forEach((k, v) -> {
            if (v > flightsForRegularPlanes) {
                planeList.add(k);
            }
        });
        return planeList;
    }

}
