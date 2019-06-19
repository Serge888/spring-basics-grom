package com.lesson6.service;

import com.lesson6.dao.FlightDao;
import com.lesson6.model.Filter;
import com.lesson6.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightDao flightDao;

    @Override
    public Flight save(Flight flight) {
        flightDao.save(flight);
        return flight;
    }

    @Override
    public Flight update(Flight flight) {
        flightDao.update(flight);
        return flight;
    }

    @Override
    public Flight delete(Long id) {
        return flightDao.delete(id);
    }

    @Override
    public Flight findById(Long id) {
        return flightDao.findById(id);
        
    }

    @Override
    public List<Flight> flightsByDate(Filter filter) {
        return flightDao.flightsByDate(filter);
    }

    @Override
    public List<Flight> mostPopularTo() {
        return flightDao.mostPopularTo();
    }

    @Override
    public List<Flight> mostPopularFrom() {
        return flightDao.mostPopularFrom();
    }
}
