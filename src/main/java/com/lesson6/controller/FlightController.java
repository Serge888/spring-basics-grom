package com.lesson6.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.model.Filter;
import com.lesson6.model.Flight;
import com.lesson6.model.Passenger;
import com.lesson6.service.FlightService;
import com.lesson6.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

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
    String save(@RequestBody Flight flight) {
        try {
            flightService.save(flight);
        } catch (Exception e) {
            e.getMessage();
            return "flight id " + flight.getId() + " was not saved";
        }
        return "flight id " + flight.getId() + " was saved";
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/update-flight", produces = "text/plain")
    public @ResponseBody
    String update(@RequestBody Flight flight) {
        try {
            List<Passenger> passengerSet = new ArrayList<>();
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


    @RequestMapping(method = RequestMethod.DELETE, value = "/delete-flight", produces = "text/plain")
    public @ResponseBody
    String delete(@RequestBody Flight flight) {
        try {
            flightService.delete(flight.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return "flight " + flight.getId() + " was not deleted";
        }
        return "flight " + flight.getId() + " was deleted";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findById-flight/{flightId}", produces = "text/plain")
    public @ResponseBody
    String findById(@PathVariable Long flightId) {
        Flight flight = new Flight();
        try {
            flight = flightService.findById(flightId);
        } catch (Exception e) {
            e.getMessage();
            return "flight id " + flight.getId() +" was not found.";
        }

        return "flight id " + flight.getId() +" was found: " + flight;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mostPopularFrom", produces = "text/plain")
    public @ResponseBody
    String mostPopularFrom() {
       List<Flight> flightList = flightService.mostPopularFrom();
       return "The most popular flights according to departure cities: " + flightList.toString();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/mostPopularTo", produces = "text/plain")
    public @ResponseBody
    String mostPopularTo() {
       List<Flight> flightList = flightService.mostPopularTo();
       return "The most popular flights according to arrival cities: " + flightList.toString();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/flightsByFilter", produces = "text/plain")
    public @ResponseBody
    String flightsByDate(@RequestBody Filter filter) {
        List<Flight> flightList;
        try {
            flightList = new ArrayList<>(flightService.flightsByDate(filter));
        } catch (Exception e) {
            e.getMessage();
            return "Flights according to the filter were not found.";
        }
        return "Flights according to the filter: " + flightList.toString();
    }

}
