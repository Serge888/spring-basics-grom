package com.lesson6.service;

import com.lesson6.model.Flight;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightService {

    Flight save(Flight flight);
    Flight update(Flight flight);
    Flight delete(Long id);
    Flight findById(Long id);


}
