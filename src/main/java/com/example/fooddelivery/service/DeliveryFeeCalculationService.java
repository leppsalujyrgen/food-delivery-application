package com.example.fooddelivery.service;

import com.example.fooddelivery.entity.City;
import com.example.fooddelivery.entity.WeatherData;
import com.example.fooddelivery.repository.WeatherDataRepository;
import com.example.fooddelivery.util.FeeCalculations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for calculating the delivery fee based on the city and vehicle type.
 */
@Service
public class DeliveryFeeCalculationService {

    private final WeatherDataRepository weatherDataRepository;
    
    private final CityService cityService;

    @Autowired
    public DeliveryFeeCalculationService(WeatherDataRepository weatherDataRepository, CityService cityService) {
        this.weatherDataRepository = weatherDataRepository;
        this.cityService = cityService;
    }

    /**
     * Calculates the delivery fee based on the specified city and vehicle type.
     * 
     * @param cityName The name of the city.
     * @param vehicleType The type of vehicle used for delivery.
     * @return The calculated delivery fee.
     * @throws IllegalArgumentException If the city or weather data is not found.
     */
    public double calculateDeliveryFee(String cityName, String vehicleType) {
    	Optional<City> requestedCity = cityService.getCityByName(cityName);
        if (requestedCity.isEmpty()) {
            throw new IllegalArgumentException("Unknown city: " + cityName);
        }
        City city = requestedCity.get();
    	
    	
        Optional<WeatherData> requestedWeatherData = weatherDataRepository.findTopByStationNameOrderByTimestampDesc(city.getWeatherStationName());
        if (requestedWeatherData.isEmpty()) {
            throw new IllegalArgumentException("Weather data not found for city: " + city.getName());
        }
        WeatherData weatherData = requestedWeatherData.get();

        double regionalBaseFee = FeeCalculations.calculateRegionalBaseFee(city.getName(), vehicleType);

        double extraFee = FeeCalculations.calculateExtraFee(weatherData, vehicleType);

        return regionalBaseFee + extraFee;
    }

    
}