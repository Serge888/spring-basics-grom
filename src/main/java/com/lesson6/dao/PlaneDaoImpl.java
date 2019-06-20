package com.lesson6.dao;

import com.lesson6.model.Plane;
import com.lesson6.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Transactional
@Repository
public class PlaneDaoImpl extends GeneralDao<Plane> implements PlaneDao {
    @Value("${yearsForOldPlanes:20}")
    private Integer yearsForOldPlanes;
    @Value("${flightsForRegularPlanes:300}")
    private Integer flightsForRegularPlanes;

    @Override
    public Plane save(Plane plane) {
        return super.save(plane);
    }

    @Override
    public Plane update(Plane plane) {
        return super.update(plane);
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
        String sql = "WITH FLIGT_FOR_YEAR AS (SELECT PLANE_ID, COUNT(PLANE_ID) COUNTS FROM FLIGHT " +
                "WHERE DATE_FLIGHT BETWEEN TO_DATE(:beginningOfYear) and TO_DATE(:endOfYear) " +
                "GROUP BY PLANE_ID) " +
                "SELECT * FROM PLANE " +
                "JOIN FLIGT_FOR_YEAR ON  FLIGT_FOR_YEAR.PLANE_ID =  PLANE.ID " +
                "WHERE COUNTS > :flightsForRegularPlanes";
        return entityManager.createNativeQuery(sql, Plane.class)
                .setParameter("beginningOfYear", DateUtil.getDateInStringBeginningOfYear(year))
                .setParameter("endOfYear", DateUtil.getDateInStringEndOfYear(year))
                .setParameter("flightsForRegularPlanes", flightsForRegularPlanes)
                .getResultList();
    }

}
