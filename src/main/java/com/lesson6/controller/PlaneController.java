package com.lesson6.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.model.Plane;
import com.lesson6.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class PlaneController {

    @Autowired
    private PlaneService planeService;

    @RequestMapping(method = RequestMethod.POST, value = "/save-plane", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) {
        Plane plane = new Plane();
        try {
            plane = mapPlane(req);
            planeService.save(plane);
        } catch (Exception e) {
            e.getMessage();
            return "plane id " + plane.getId() + " was not saved";
        }
        return "plane id " + plane.getId() + " was saved";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update-plane", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req) {
        Plane plane = new Plane();
        try {
            Plane planeNew = mapPlane(req);
            plane = planeService.findById(planeNew.getId());
            plane.setModel(planeNew.getModel());
            plane.setCode(planeNew.getCode());
            plane.setYearProduced(planeNew.getYearProduced());
            plane.setAvgFuelConsumption(planeNew.getAvgFuelConsumption());
            planeService.update(plane);
        } catch (Exception e) {
            e.getMessage();
            return "plane id " + plane.getId() + " was not updated";
        }
        return "plane id " + plane.getId() + " was updated";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/delete-plane", produces = "text/plain")
    public @ResponseBody
    String delete(HttpServletRequest req) {
        Plane plane = new Plane();
        try {
            plane = mapPlane(req);
            planeService.delete(plane.getId());
        } catch (Exception e) {
            e.getMessage();
            return "plane id " + plane.getId() + " was not deleted";
        }
        return "plane id " + plane.getId() + " was deleted";
    }



    @RequestMapping(method = RequestMethod.POST, value = "/findById-plane", produces = "text/plain")
    public @ResponseBody
    String findById(HttpServletRequest req) {
        Plane plane = new Plane();
        try {
            plane = planeService.findById(mapPlane(req).getId());
        } catch (Exception e) {
            e.getMessage();
            return "plane " + plane.getId() +" was not found";
        }
        return "plane " + plane.getId() +" was found: " + plane;
    }



    private Plane mapPlane(HttpServletRequest req) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Plane plane = new Plane();
        try (ServletInputStream inputStream = req.getInputStream()) {
            plane = objectMapper.readValue(inputStream, plane.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException ("There some problem in mapFlight method");
        }
        return plane;
    }
}
