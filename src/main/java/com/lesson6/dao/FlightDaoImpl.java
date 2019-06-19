package com.lesson6.dao;

import com.lesson6.model.Filter;
import com.lesson6.model.Flight;
import com.lesson6.model.Plane;
import com.lesson6.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class FlightDaoImpl implements FlightDao {
    @Value("${mostPopular:10}")
    private Integer mostPopular;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Flight save(Flight flight) {
        entityManager.persist(flight);
        return flight;
    }

    @Override
    public Flight update(Flight flight) {
        entityManager.merge(flight);
        return flight;
    }

    @Override
    public Flight delete(Long id) {
        Flight flight = entityManager.find(Flight.class, id);
        entityManager.remove(flight);
        return flight;
    }

    @Override
    public Flight findById(Long id) {
        return entityManager.find(Flight.class, id);
        
    }

    @Override
    public List<Flight> flightsByDate(Filter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = builder.createQuery(Flight.class);
        Root<Flight> rootFlight = criteriaQuery.from(Flight.class);
        criteriaQuery.select(rootFlight);
        Join<Flight, Plane> planeJoin = rootFlight.join("plane");
        List<Predicate> restrictions = new ArrayList<>();

        if (filter.getCityFrom() != null
                || filter.getCityTo() != null
                || filter.getModel() != null
                || filter.getDateFrom() != null
                || filter.getDateTo() != null) {

            if (filter.getDateFrom() != null || filter.getDateTo() != null) {
                Date dateFrom = new Date();
                if (filter.getDateFrom() != null) {
                    dateFrom = filter.getDateFrom();
                }
                Date dateTo = DateUtil.localDateToDate(DateUtil.dateToLocalDate(dateFrom).plusMonths(1));
                if (filter.getDateTo() != null) {
                    dateTo = filter.getDateTo();
                }
                restrictions.add(builder.greaterThanOrEqualTo(rootFlight.get("dateFlight"), dateFrom));
                restrictions.add(builder.lessThanOrEqualTo(rootFlight.get("dateFlight"), dateTo));
            }
            if (filter.getCityFrom() != null) {
                restrictions.add(builder.equal(rootFlight.get("cityFrom"), filter.getCityFrom()));
            }
            if (filter.getCityTo() != null) {
                restrictions.add(builder.equal(rootFlight.get("cityTo"), filter.getCityTo()));
            }
            if (filter.getModel() != null) {
                restrictions.add(builder.equal(planeJoin.get("model"), filter.getModel()));
            }

            if (!restrictions.isEmpty()) {
                Predicate[] predicates = new Predicate[restrictions.size()];
                restrictions.toArray(predicates);
                criteriaQuery.where(predicates);
                return entityManager.createQuery(criteriaQuery).getResultList();
            }
        }
        return null;
    }


    @Override
    public List<Flight> mostPopularTo() {
        String sql = "WITH MOST_POPULAR_CITY AS (SELECT CITY_TO CITY, count(CITY_TO) COUNTS," +
                "    RANK() OVER (order by count(CITY_TO) DESC) RANK_COUNT from FLIGHT group by CITY_TO)" +
                "    SELECT * FROM FLIGHT" +
                "            join MOST_POPULAR_CITY on FLIGHT.CITY_TO = MOST_POPULAR_CITY.CITY" +
                "        WHERE RANK_COUNT < 11";
        return entityManager.createNativeQuery(sql, Flight.class)
                .setMaxResults(mostPopular)
                .getResultList();
    }


    @Override
    public List<Flight> mostPopularFrom() {
        String sql = "WITH MOST_POPULAR_CITY AS (SELECT CITY_FROM CITY, count(CITY_FROM) COUNTS," +
                "    RANK() OVER (order by count(CITY_FROM) DESC) RANK_COUNT from FLIGHT group by CITY_FROM)" +
                "    SELECT * FROM FLIGHT" +
                "            join MOST_POPULAR_CITY on FLIGHT.CITY_FROM = MOST_POPULAR_CITY.CITY" +
                "        WHERE RANK_COUNT < 11";
        return entityManager.createNativeQuery(sql, Flight.class)
                .setMaxResults(mostPopular)
                .getResultList();
    }


    @Override
    public List<Flight> flightsForYear(int year) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = builder.createQuery(Flight.class);
        Root<Flight> flightRoot = criteriaQuery.from(Flight.class);
        criteriaQuery.select(flightRoot);
        Predicate predicate = builder.between(flightRoot.get("dateFlight"), DateUtil.begingOfYear(year), DateUtil.endOfYear(year));
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }


}
