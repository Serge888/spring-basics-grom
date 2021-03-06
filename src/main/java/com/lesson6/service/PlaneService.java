package com.lesson6.service;

import com.lesson6.model.Plane;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaneService {

    Plane save(Plane plane);
    Plane update(Plane plane);
    Plane delete(Long id);
    Plane findById(Long id);
    List<Plane> oldPlanes();
    List<Plane> regularPlanes(int year);


}
