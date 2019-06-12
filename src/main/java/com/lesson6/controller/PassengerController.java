package com.lesson6.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.model.Passenger;
import com.lesson6.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @RequestMapping(method = RequestMethod.POST, value = "/save-passenger", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) {
        Passenger passenger = new Passenger();
        try {
            passenger = mapPassenger(req);
            passengerService.save(passenger);
        } catch (Exception e) {
            e.getMessage();
            return "passenger id " + passenger.getId() + " was not saved";
        }
        return "passenger id " + passenger.getId() + " was saved";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update-passenger", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req) {
        Passenger passenger = new Passenger();
        try {
            Passenger newPassenger = mapPassenger(req);
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


    @RequestMapping(method = RequestMethod.POST, value = "/delete-passenger", produces = "text/plain")
    public @ResponseBody
    String delete(HttpServletRequest req) {
        Passenger passenger = new Passenger();
        try {
            passenger = mapPassenger(req);
            passengerService.delete(passenger.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return "passenger id " + passenger.getId() + " was not deleted";

        }
        return "passenger id " + passenger.getId() + " was deleted " + passenger;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findById-passenger", produces = "text/plain")
    public @ResponseBody
    String findById(HttpServletRequest req) {
        Passenger passenger = new Passenger();
        try {
            passenger = mapPassenger(req);
            passenger = passengerService.findById(passenger.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return "passenger " + passenger.getId() +" was not found.";
        }
        return "passenger " + passenger.getId() +" was found: " + passenger;
    }

    private Passenger mapPassenger(HttpServletRequest req) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Passenger passenger = new Passenger();
        try (ServletInputStream inputStream = req.getInputStream()) {
            passenger = objectMapper.readValue(inputStream, passenger.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException ("There some problem in mapPassenger method");
        }
        return passenger;
    }
}
