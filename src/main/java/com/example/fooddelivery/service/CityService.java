package com.example.fooddelivery.service;

import com.example.fooddelivery.entity.City;
import com.example.fooddelivery.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    // Get all cities
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // Get city by ID
    public City getCityById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        return city.orElse(null);
    }

    // Create a new city
    public City createCity(City city) {
        return cityRepository.save(city);
    }

    // Update an existing city
    public City updateCity(Long id, City cityDetails) {
        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setName(cityDetails.getName());
            city.setWeatherStationName(cityDetails.getWeatherStationName());
            return cityRepository.save(city);
        } else {
            return null; // Or throw an exception, depending on your requirement
        }
    }

    // Delete a city
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

	public Optional<City> getCityByName(String cityName) {
		return cityRepository.findTopByName(cityName);
	}
}
