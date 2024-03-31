package com.example.fooddelivery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String weatherStationName;

    public City() {
    }

    public City(String name, String weatherStationWmo) {
        this.name = name;
        this.weatherStationName = weatherStationWmo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherStationName() {
        return weatherStationName;
    }

    public void setWeatherStationName(String weatherStationName) {
        this.weatherStationName = weatherStationName;
    }
}
