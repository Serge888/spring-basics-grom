package com.lesson6.dao;

import com.lesson6.model.Passenger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class PassengerDaoImpl implements PassengerDao {

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
    public List<Passenger> regularPassengers(int year) {
        return null;
    }
}
