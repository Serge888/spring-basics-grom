package com.lesson6.dao;

import com.lesson6.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDao {

    Passenger save(Passenger passenger);
    Passenger update(Passenger passenger);
    Passenger delete(Long id);
    Passenger findById(Long id);
    List<Passenger> regularPassengers(Integer year);


}
