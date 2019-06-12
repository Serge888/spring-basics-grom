package com.lesson6.dao;

import com.lesson6.model.Filter;
import com.lesson6.model.Flight;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class FlightDaoImpl implements FlightDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Flight save(Flight flight) {
        entityManager.persist(flight);
        return flight;
    }

    @Override
    public Flight update(Flight flight) {
        entityManager.merge(flight);
        return flight;
    }

    @Override
    public Flight delete(Long id) {
        Flight flight = entityManager.find(Flight.class, id);
        entityManager.remove(flight);
        return flight;
    }

    @Override
    public Flight findById(Long id) {
        return entityManager.find(Flight.class, id);
        
    }

    @Override
    public List<Flight> flightsByDate(Filter filter) {
        return null;
    }

    @Override
    public List<Flight> mostPopularTo() {
        return null;
    }

    @Override
    public List<Flight> mostPopularFrom() {
        return null;
    }
}
