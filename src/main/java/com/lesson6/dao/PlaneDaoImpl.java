package com.lesson6.dao;

import com.lesson6.model.Flight;
import com.lesson6.model.Plane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Transactional
@Repository
public class PlaneDaoImpl implements PlaneDao {
    @Value("${yearsForOldPlanes:20}")
    private Integer yearsForOldPlanes;
    @Value("${flightsForRegularPlanes:300}")
    private Integer flightsForRegularPlanes;


    @PersistenceContext
    private EntityManager entityManager;

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
        Date dateOld = Date.from(twentyYearsEarlier.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return entityManager.createNamedQuery("Plane.OldPlanes", Plane.class)
                .setParameter("date", dateOld)
                .getResultList();
    }


    @Override
    public List<Plane> regularPlanes(int year) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = builder.createQuery(Flight.class);
        Root<Flight> flightRoot = criteriaQuery.from(Flight.class);
        criteriaQuery.select(flightRoot);
        Predicate predicate = builder.between(flightRoot.get("dateFlight"), begingOfYear(year), endOfYear(year));
        criteriaQuery.where(predicate);
        List<Flight> flightList = entityManager.createQuery(criteriaQuery).getResultList();

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


    private Date begingOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private Date endOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
}
