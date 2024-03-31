package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.WeatherData;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

	Optional<WeatherData> findTopByStationNameOrderByTimestamp(String stationName);
}
