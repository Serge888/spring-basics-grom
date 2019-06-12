package com.lesson6.service;

import com.lesson6.dao.FlightDao;
import com.lesson6.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

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
}
