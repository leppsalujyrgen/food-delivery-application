package com.example.fooddelivery.service;

import com.example.fooddelivery.entity.City;
import com.example.fooddelivery.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for performing CRUD operations on City entities.
 */
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    /**
     * Retrieves all cities from the database.
     * 
     * @return A list of all cities.
     */
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    /**
     * Retrieves a city by its ID.
     * 
     * @param id The ID of the city to retrieve.
     * @return The city with the specified ID, or null if not found.
     */
    public City getCityById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        return city.orElse(null);
    }

    /**
     * Creates a new city in the database.
     * 
     * @param city The city object to create.
     * @return The created city.
     */
    public City createCity(City city) {
        return cityRepository.save(city);
    }

    /**
     * Updates an existing city in the database.
     * 
     * @param id The ID of the city to update.
     * @param cityDetails The updated details of the city.
     * @return The updated city, or null if the city with the specified ID was not found.
     */
    public City updateCity(Long id, City cityDetails) {
        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setName(cityDetails.getName());
            city.setWeatherStationName(cityDetails.getWeatherStationName());
            return cityRepository.save(city);
        } else {
            return null; 
        }
    }

    /**
     * Deletes a city from the database by its ID.
     * 
     * @param id The ID of the city to delete.
     */
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    /**
     * Retrieves a city by its name.
     * 
     * @param cityName The name of the city to retrieve.
     * @return An Optional containing the city with the specified name, or empty if not found.
     */
    public Optional<City> getCityByName(String cityName) {
        return cityRepository.findTopByName(cityName);
    }
}