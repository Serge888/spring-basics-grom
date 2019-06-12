package com.lesson6.service;

import com.lesson6.model.Passenger;
import org.springframework.stereotype.Service;

@Service
public interface PassengerService {

    Passenger save(Passenger passenger);
    Passenger update(Passenger passenger);
    Passenger delete(Long id);
    Passenger findById(Long id);


}
