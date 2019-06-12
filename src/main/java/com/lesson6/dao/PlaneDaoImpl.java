package com.lesson6.dao;

import com.lesson6.model.Plane;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class PlaneDaoImpl implements PlaneDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Plane save(Plane plane) {
        entityManager.persist(plane);
        return plane;
    }

    @Override
    public Plane update(Plane plane) {
        entityManager.merge(plane);
        return plane;
    }

    @Override
    public Plane delete(Long id) {
        Plane plane = entityManager.find(Plane.class, id);
        entityManager.remove(plane);
        return plane;
    }

    @Override
    public Plane findById(Long id) {
        return entityManager.find(Plane.class, id);

    }

    @Override
    public List<Plane> oldPlanes() {
        return null;
    }

    @Override
    public List<Plane> regularPlanes(int year) {
        return null;
    }
}
