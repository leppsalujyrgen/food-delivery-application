package com.example.fooddelivery.controller;

import com.example.fooddelivery.entity.City;
import com.example.fooddelivery.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling requests related to cities.
 * This class provides endpoints for retrieving, creating, updating, and deleting cities.
 */
@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * Retrieves a list of all cities.
     *
     * @return the list of cities
     */
    @GetMapping
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    /**
     * Retrieves a city by its ID.
     *
     * @param id the ID of the city to retrieve
     * @return the city with the specified ID
     */
    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    /**
     * Creates a new city with the specified name and weather station name.
     *
     * @param cityName          the name of the city
     * @param weatherStationName the name of the weather station associated with the city
     * @return the newly created city
     */
    @PostMapping("/{cityName}/{weatherStationName}")
    public City createCity(@PathVariable String cityName, @PathVariable String weatherStationName) {
        return cityService.createCity(new City(cityName, weatherStationName));
    }

    /**
     * Updates an existing city with the specified ID.
     *
     * @param id          the ID of the city to update
     * @param cityDetails the updated details of the city
     * @return the updated city
     */
    @PutMapping("/{id}")
    public City updateCity(@PathVariable Long id, @RequestBody City cityDetails) {
        return cityService.updateCity(id, cityDetails);
    }

    /**
     * Deletes a city with the specified ID.
     *
     * @param id the ID of the city to delete
     */
    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}