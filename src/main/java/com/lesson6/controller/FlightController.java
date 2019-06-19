package com.lesson6.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.model.Filter;
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


    @RequestMapping(method = RequestMethod.PUT, value = "/update-flight", produces = "text/plain")
    public @ResponseBody
    String update(HttpServletRequest req) {
        Flight flight = new Flight();
        try {
            flight = mapFlight(req);
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

    @RequestMapping(method = RequestMethod.GET, value = "/findById-flight", produces = "text/plain")
    public @ResponseBody
    String findById(Long id) {
        Flight flight = new Flight();
        try {
            flight = flightService.findById(id);
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
    String flightsByDate(HttpServletRequest req) {
        List<Flight> flightList = new ArrayList<>();
        try {
            Filter filter = mapFilter(req);
            flightList.addAll(flightService.flightsByDate(filter));
        } catch (IOException e) {
            e.getMessage();
            return "Flights according to the filter were not found.";
        }
        return "Flights according to the filter: " + flightList.toString();
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

    private Filter mapFilter(HttpServletRequest req) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Filter filter = new Filter();
        try (ServletInputStream inputStream = req.getInputStream()) {
            filter = objectMapper.readValue(inputStream, filter.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException ("There some problem in mapFilter method");
        }
        return filter;
    }
}
