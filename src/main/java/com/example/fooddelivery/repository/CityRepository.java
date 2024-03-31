package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.City;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and managing City entities in the database.
 */
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * Finds the top City entity with the given city name.
     * 
     * @param cityName The name of the city to search for.
     * @return An Optional containing the found City entity, or empty if not found.
     */
    Optional<City> findTopByName(String cityName);
}