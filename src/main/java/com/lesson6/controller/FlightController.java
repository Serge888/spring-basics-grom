package com.lesson6.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.model.Flight;
import com.lesson6.model.Passenger;
import com.lesson6.service.FlightService;
import com.lesson6.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
public class FlightController {
    private FlightService flightService;
    private PassengerService passengerService;

    @Autowired
    public FlightController(FlightService flightService, PassengerService passengerService) {
        this.flightService = flightService;
        this.passengerService = passengerService;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/save-flight", produces = "text/plain")
    public @ResponseBody
    String save(HttpServletRequest req) {
        Flight flight = new Flight();
        try {
            flight = mapFlight(req);
            flightService.save(flight);
        } catch (Exception e) {
            e.getMessage();
            return "flight id " + flight.getId() + " was not saved";
        }
        return "flight id " + flight.getId() + " was saved";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update-flight", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req) {
        Flight flight = new Flight();
        try {
            flight = mapFlight(req);
            Set<Passenger> passengerSet = new HashSet<>();
            for (Passenger passenger : flight.getPassengers()) {
                passengerSet.add(passengerService.findById(passenger.getId()));
            }
            flight.setPassengers(passengerSet);
            flightService.update(flight);
        } catch (Exception e) {
            e.getCause();
            return "flight id " + flight.getId() + " was not updated";
        }
        return "flight id " + flight.getId() + " was updated";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/delete-flight", produces = "text/plain")
    public @ResponseBody
    String delete(HttpServletRequest req) {
        Flight flight = new Flight();
        try {
            flight = mapFlight(req);
            flightService.delete(flight.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return "flight " + flight.getId() + " was not deleted";
        }
        return "flight " + flight.getId() + " was deleted";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findById-flight", produces = "text/plain")
    public @ResponseBody
    String findById(HttpServletRequest req) {
        Flight flight = new Flight();
        try {
            flight = flightService.findById(mapFlight(req).getId());
        } catch (Exception e) {
            e.getMessage();
            return "flight id " + flight.getId() +" was not found.";
        }

        return "flight id " + flight.getId() +" was found: " + flight;
    }


    private Flight mapFlight(HttpServletRequest req) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Flight flight = new Flight();
        try (ServletInputStream inputStream = req.getInputStream()) {
            flight = objectMapper.readValue(inputStream, flight.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException ("There some problem in mapFlight method");
        }
        return flight;
    }
}
