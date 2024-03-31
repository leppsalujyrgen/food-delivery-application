package com.example.fooddelivery.controller;

import com.example.fooddelivery.entity.City;
import com.example.fooddelivery.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    // Get all cities
    @GetMapping
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    // Get city by ID
    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    // Create a new city
    @PostMapping("/{cityName}/{weatherStationName}")
    public City createCity(@PathVariable String cityName, @PathVariable String weatherStationName) {
        return cityService.createCity(new City(cityName, weatherStationName));
    }

    // Update an existing city
    @PutMapping("/{id}")
    public City updateCity(@PathVariable Long id, @RequestBody City cityDetails) {
        return cityService.updateCity(id, cityDetails);
    }

    // Delete a city
    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}