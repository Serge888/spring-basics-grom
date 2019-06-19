package com.lesson6.service;

import com.lesson6.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassengerService {

    Passenger save(Passenger passenger);
    Passenger update(Passenger passenger);
    Passenger delete(Long id);
    Passenger findById(Long id);
    List<Passenger> regularPassengers(Integer year);


}
