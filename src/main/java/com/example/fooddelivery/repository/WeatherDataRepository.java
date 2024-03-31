package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.WeatherData;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and managing WeatherData entities in the database.
 */
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * Finds the top WeatherData entity with the given station name, ordered by timestamp.
     * 
     * @param stationName The name of the weather station to search for.
     * @return An Optional containing the found WeatherData entity, or empty if not found.
     */
    Optional<WeatherData> findTopByStationNameOrderByTimestampDesc(String stationName);
}
