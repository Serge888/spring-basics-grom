package com.lesson6.service;

import com.lesson6.dao.PassengerDao;
import com.lesson6.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerDao passengerDao;

    @Override
    public Passenger save(Passenger passenger) {
        passengerDao.save(passenger);
        return passenger;
    }

    @Override
    public Passenger update(Passenger passenger) {
        passengerDao.update(passenger);
        return passenger;
    }

    @Override
    public Passenger delete(Long id) {
        return passengerDao.delete(id);
    }

    @Override
    public Passenger findById(Long id) {
        return passengerDao.findById(id);
        
    }

    @Override
    public List<Passenger> regularPassengers(Integer year) {
        return passengerDao.regularPassengers(year);
    }
}
