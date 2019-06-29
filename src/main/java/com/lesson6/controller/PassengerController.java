package com.lesson6.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.model.Passenger;
import com.lesson6.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @RequestMapping(method = RequestMethod.POST, value = "/save-passenger", produces = "text/plain")
    public @ResponseBody
    String save(@RequestBody Passenger passenger) {
        try {
            passengerService.save(passenger);
        } catch (Exception e) {
            e.getMessage();
            return "passenger id " + passenger.getId() + " was not saved";
        }
        return "passenger id " + passenger.getId() + " was saved";
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/update-passenger", produces = "text/plain")
    public @ResponseBody
    String update(@RequestBody Passenger newPassenger) {
        Passenger passenger = new Passenger();
        try {
            passenger = passengerService.findById(newPassenger.getId());
            passenger.setLastName(newPassenger.getLastName());
            passenger.setNationality(newPassenger.getNationality());
            passenger.setPassportCode(newPassenger.getPassportCode());
            passenger.setDateOfBirth(newPassenger.getDateOfBirth());
            passenger.setFlights(newPassenger.getFlights());
            passengerService.update(passenger);
        } catch (Exception e) {
            e.getMessage();
            return "passenger id " + passenger.getId() + " was not updated";
        }
        return "passenger id " + passenger.getId() + " was updated " + passenger;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/delete-passenger", produces = "text/plain")
    public @ResponseBody
    String delete(@RequestBody Passenger passenger) {
        try {
            passengerService.delete(passenger.getId());
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
            return "passenger id " + passenger.getId() + " was not deleted";

        }
        return "passenger id " + passenger.getId() + " was deleted " + passenger;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findById-passenger/{passengerId}", produces = "text/plain")
    public @ResponseBody
    String findById(@PathVariable Long passengerId) {
        Passenger passenger = new Passenger();
        try {
            passenger = passengerService.findById(passengerId);
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
            return "passenger " + passenger.getId() +" was not found.";
        }
        return "passenger " + passenger.getId() +" was found: " + passenger;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/regularPassengers", produces = "text/plain")
    public @ResponseBody
    String regularPassengers(@RequestParam Integer year) {
        List<Passenger> passengerList = passengerService.regularPassengers(year);
        return "Passengers that have more than 25 flights per year: " + passengerList.toString();
    }

}
