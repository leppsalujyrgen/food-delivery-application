package com.example.fooddelivery.service;

import com.example.fooddelivery.entity.City;
import com.example.fooddelivery.entity.WeatherData;
import com.example.fooddelivery.repository.WeatherDataRepository;
import com.example.fooddelivery.util.FeeCalculations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeCalculationService {

    private final WeatherDataRepository weatherDataRepository;
    
    private final CityService cityService;

    @Autowired
    public DeliveryFeeCalculationService(WeatherDataRepository weatherDataRepository, CityService cityService) {
        this.weatherDataRepository = weatherDataRepository;
        this.cityService = cityService;
    }

    public double calculateDeliveryFee(String cityName, String vehicleType) {
    	// Get the city from database
    	Optional<City> requestedCity = cityService.getCityByName(cityName);
        if (requestedCity.isEmpty()) {
            throw new IllegalArgumentException("Unknown city: " + cityName);
        }
        City city = requestedCity.get();
    	
    	
        // Get the latest weather data for the specified city
        Optional<WeatherData> requestedWeatherData = weatherDataRepository.findTopByStationNameOrderByTimestamp(city.getWeatherStationName());
        if (requestedWeatherData.isEmpty()) {
            throw new IllegalArgumentException("Weather data not found for city: " + city.getName());
        }
        WeatherData weatherData = requestedWeatherData.get();

        // Calculate regional base fee
        double regionalBaseFee = FeeCalculations.calculateRegionalBaseFee(city.getName(), vehicleType);

        // Calculate extra fees based on weather conditions
        double extraFee = FeeCalculations.calculateExtraFee(weatherData, vehicleType);

        // Total delivery fee
        return regionalBaseFee + extraFee;
    }

    
}