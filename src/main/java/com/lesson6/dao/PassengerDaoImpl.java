package com.lesson6.dao;

import com.lesson6.model.Flight;
import com.lesson6.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class PassengerDaoImpl implements PassengerDao {
    @Value("${flightsForRegularPassenger:25}")
    private Integer flightsForRegularPassenger;
    private FlightDao flightDao;

    @Autowired
    public PassengerDaoImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Passenger save(Passenger passenger) {
        entityManager.persist(passenger);
        return passenger;
    }

    @Override
    public Passenger update(Passenger passenger) {
        entityManager.merge(passenger);
        return passenger;
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
        List<Flight> flightList = flightDao.flightsForYear(year);
        Map<Passenger, Integer> planeMap = new HashMap<>();
        Passenger passenger;
        for (Flight flight : flightList) {
            for (Passenger flightPassenger : flight.getPassengers()) {
                passenger = flightPassenger;
                if (!planeMap.containsKey(passenger)) {
                    planeMap.put(passenger, 1);
                } else {
                    planeMap.put(passenger, planeMap.get(passenger) + 1);
                }
            }
        }
        List<Passenger> passengerList = new ArrayList<>();
        planeMap.forEach((k, v) -> {
            if (v > flightsForRegularPassenger) {
                passengerList.add(k);
            }
        });
        return passengerList;
    }
}
