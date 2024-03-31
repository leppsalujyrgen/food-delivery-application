package com.example.fooddelivery.service;

import com.example.fooddelivery.entity.WeatherData;
import com.example.fooddelivery.repository.WeatherDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    public WeatherDataService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public void saveWeatherData(WeatherData weatherData) {
        weatherDataRepository.save(weatherData);
    }

    // Add other methods as needed for weather data retrieval, processing, etc.
}
