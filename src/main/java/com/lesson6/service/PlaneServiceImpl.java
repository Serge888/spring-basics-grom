package com.lesson6.service;

import com.lesson6.dao.PlaneDao;
import com.lesson6.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class PlaneServiceImpl implements PlaneService {

    @Autowired
    private PlaneDao planeDao;

    @Override
    public Plane save(Plane plane) {
        planeDao.save(plane);
        return plane;
    }

    @Override
    public Plane update(Plane plane) {
        planeDao.update(plane);
        return plane;
    }

    @Override
    public Plane delete(Long id) {
        return planeDao.delete(id);
    }


    @Override
    public Plane findById(Long id) {
        return planeDao.findById(id);

    }

    @Override
    public List<Plane> oldPlanes() {
        return planeDao.oldPlanes();
    }

    @Override
    public List<Plane> regularPlanes(int year) {
        return planeDao.regularPlanes(year);
    }
}
