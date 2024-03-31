package com.example.fooddelivery.util;

import com.example.fooddelivery.entity.WeatherData;

public class FeeCalculations {

    public static double calculateRegionalBaseFee(String city, String vehicleType) {
        if ("Tallinn".equals(city)) {
            if ("Car".equals(vehicleType)) {
                return 4.0;
            } else if ("Scooter".equals(vehicleType)) {
                return 3.5;
            } else if ("Bike".equals(vehicleType)) {
                return 3.0;
            } else {
                throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
            }
        } else if ("Tartu".equals(city)) {
            if ("Car".equals(vehicleType)) {
                return 3.5;
            } else if ("Scooter".equals(vehicleType)) {
                return 3.0;
            } else if ("Bike".equals(vehicleType)) {
                return 2.5;
            } else {
                throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
            }
        } else if ("Pärnu".equals(city)) {
            if ("Car".equals(vehicleType)) {
                return 3.0;
            } else if ("Scooter".equals(vehicleType)) {
                return 2.5;
            } else if ("Bike".equals(vehicleType)) {
                return 2.0;
            } else {
                throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
            }
        } else {
            throw new IllegalArgumentException("Invalid city: " + city);
        }
    }

    public static double calculateExtraFee(WeatherData weatherData, String vehicleType) {
        double extraFee = 0.0;
        // Extra fee based on air temperature
        if (vehicleType.equals("Scooter") || vehicleType.equals("Bike")) {
            double airTemperature = weatherData.getAirTemperature();
            if (airTemperature < -10) {
                extraFee += 1.0;
            } else if (airTemperature >= -10 && airTemperature < 0) {
                extraFee += 0.5;
            }
        }

        // Extra fee based on wind speed
        if (vehicleType.equals("Bike")) {
            double windSpeed = weatherData.getWindSpeed();
            if (windSpeed >= 10 && windSpeed < 20) {
                extraFee += 0.5;
            } else if (windSpeed >= 20) {
                throw new IllegalArgumentException("Usage of selected vehicle type is forbidden");
            }
        }

        // Extra fee based on weather phenomenon
        String weatherPhenomenon = weatherData.getWeatherPhenomenon().toLowerCase();
        if (vehicleType.equals("Scooter") || vehicleType.equals("Bike")) {
            if (weatherPhenomenon.contains("snow") || weatherPhenomenon.contains("sleet")) {
                extraFee += 1.0;
            } else if (weatherPhenomenon.contains("rain")) {
                extraFee += 0.5;
            } else if (weatherPhenomenon.contains("glaze") || weatherPhenomenon.contains("hail") || weatherPhenomenon.contains("thunder")) {
                throw new IllegalArgumentException("Usage of selected vehicle type is forbidden");
            }
        }

        return extraFee;
    }

}
