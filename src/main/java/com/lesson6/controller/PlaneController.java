package com.lesson6.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.model.Plane;
import com.lesson6.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class PlaneController {

    @Autowired
    private PlaneService planeService;

    @RequestMapping(method = RequestMethod.POST, value = "/save-plane", produces = "text/plain")
    public @ResponseBody
    String save(@RequestBody Plane plane) {
        try {
            planeService.save(plane);
        } catch (Exception e) {
            e.getMessage();
            return "plane id " + plane.getId() + " was not saved";
        }
        return "plane id " + plane.getId() + " was saved";
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/update-plane", produces = "text/plain")
    public @ResponseBody
    String update(@RequestBody Plane planeNew) {
        Plane plane = new Plane();
        try {
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


    @RequestMapping(method = RequestMethod.DELETE, value = "/delete-plane", produces = "text/plain")
    public @ResponseBody
    String delete(@RequestBody Plane plane) {
        try {
            planeService.delete(plane.getId());
        } catch (Exception e) {
            e.getMessage();
            return "plane id " + plane.getId() + " was not deleted";
        }
        return "plane id " + plane.getId() + " was deleted";
    }



    @RequestMapping(method = RequestMethod.GET, value = "/findById-plane/{planeId}", produces = "text/plain")
    public @ResponseBody
    String findById(@PathVariable Long planeId) {
        Plane plane = new Plane();
        try {
            plane = planeService.findById(planeId);
        } catch (Exception e) {
            e.getMessage();
            return "plane " + plane.getId() +" was not found";
        }
        return "plane " + plane.getId() +" was found: " + plane;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/oldPlanes", produces = "text/plain")
    public @ResponseBody
    String oldPlanes() {
        List<Plane> planeList = planeService.oldPlanes();
        return "Planes that are older than 20 years : " + planeList.toString();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/regularPlanes", produces = "text/plain")
    public @ResponseBody
    String regularPlanes(@RequestParam int year) {
        List<Plane> planeList = planeService.regularPlanes(year);
        return "Planes that have more than 300 flits per year : " + planeList.toString();
    }

}
