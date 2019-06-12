package com.lesson6.dao;

import com.lesson6.model.Plane;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneDao {

    Plane save(Plane plane);
    Plane update(Plane plane);
    Plane delete(Long id);
    Plane findById(Long id);
    List<Plane> oldPlanes();
    List<Plane> regularPlanes(int year);


}
