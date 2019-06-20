package com.lesson6.dao;

import com.lesson6.model.Passenger;
import com.lesson6.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class PassengerDaoImpl extends GeneralDao<Passenger> implements PassengerDao {
    @Value("${flightsForRegularPassenger:25}")
    private Integer flightsForRegularPassenger;

    @Override
    public Passenger save(Passenger passenger) {
        return super.save(passenger);
    }

    @Override
    public Passenger update(Passenger passenger) {
        return super.update(passenger);
    }

    @Override
    public Passenger delete(Long id) {
        Passenger passenger = entityManager.find(Passenger.class, id);
        entityManager.remove(passenger);
        return passenger;
    }

    @Override
    public Passenger findById(Long id) {
        return entityManager.find(Passenger.class, id);
        
    }

    @Override
    public List<Passenger> regularPassengers(Integer year) {
        String sql = "WITH FLIGHTS_FOR_YEAR AS (SELECT FLIGHT_ID, PASSENGER_ID FROM FLIGHT_PASSENGER " +
                "JOIN FLIGHT ON FLIGHT_ID = FLIGHT.ID " +
                "WHERE FLIGHT.DATE_FLIGHT BETWEEN TO_DATE(:beginningOfYear) and TO_DATE(:endOfYear)) " +
                "SELECT * FROM PASSENGER " +
                "JOIN (SELECT PASSENGER_ID PASS_ID, COUNT(PASSENGER_ID) COUNTS FROM FLIGHTS_FOR_YEAR GROUP BY PASSENGER_ID) " +
                "ON PASS_ID = PASSENGER.ID " +
                "WHERE COUNTS > :flightsForRegularPassenger";
        return entityManager.createNativeQuery(sql, Passenger.class)
                .setParameter("beginningOfYear", DateUtil.getDateInStringBeginningOfYear(year))
                .setParameter("endOfYear", DateUtil.getDateInStringEndOfYear(year))
                .setParameter("flightsForRegularPassenger", flightsForRegularPassenger)
                .getResultList();
    }
}
